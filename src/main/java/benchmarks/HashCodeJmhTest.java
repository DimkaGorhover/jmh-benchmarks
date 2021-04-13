/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package benchmarks;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.openjdk.jmh.annotations.*;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Horkhover Dmytro
 * @since 2018-11-30
 */
@SuppressWarnings({"unused", "Duplicates", "SimplifiableIfStatement", "WeakerAccess", "DefaultAnnotationParam"})
@State(Scope.Thread)
@Measurement(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@Warmup(iterations = 4, time = 2, timeUnit = TimeUnit.SECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(4)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class HashCodeJmhTest {

    public ApacheCommons3User apacheCommons3User;
    public GuavaUser          guavaUser;
    public SimpleUser         simpleUser;
    public Java7User          java7User;
    public LombokUser         lombokUser;

    @Setup
    public void setup() {
        simpleUser = new SimpleUser();
        simpleUser.setId(ThreadLocalRandom.current().nextInt(10_000));
        simpleUser.setName(UUID.randomUUID().toString());
        simpleUser.setPhones(IntStream.range(0, 5)
                .mapToObj(ignore -> UUID.randomUUID().toString())
                .toArray(String[]::new));
        simpleUser.setCards(ThreadLocalRandom.current().doubles(5)
                .boxed()
                .collect(Collectors.toList()));
        simpleUser.setGender(ThreadLocalRandom.current().nextDouble() > 0.5 ? User.Gender.MALE : User.Gender.FEMALE);

        apacheCommons3User = ApacheCommons3User.of(simpleUser);
        guavaUser = GuavaUser.of(apacheCommons3User);
        java7User = Java7User.of(guavaUser);
        lombokUser = LombokUser.of(java7User);
    }

    @Benchmark
    public int test_ApacheCommons3() { return apacheCommons3User.hashCode(); }

    @Benchmark
    public int test_Guava() { return guavaUser.hashCode(); }

    @Benchmark
    public int test_Intellij() { return simpleUser.hashCode(); }

    @Benchmark
    public int test_Java7() { return java7User.hashCode(); }

    @Benchmark
    public int test_Lombok() { return lombokUser.hashCode(); }


    static class ApacheCommons3User implements User {
        private int          id;
        private String       name;
        private String[]     phones;
        private List<Double> cards;
        private Gender       gender;

        static ApacheCommons3User of(User user) {
            ApacheCommons3User u = new ApacheCommons3User();
            u.setId(user.getId());
            u.setName(user.getName());
            u.setPhones(Arrays.copyOf(user.getPhones(), user.getPhones().length));
            u.setCards(new ArrayList<>(user.getCards()));
            u.setGender(user.getGender());
            return u;
        }

        public int getId() { return id; }

        public void setId(int id) { this.id = id; }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public String[] getPhones() { return phones; }

        public void setPhones(String[] phones) { this.phones = phones; }

        public List<Double> getCards() { return cards; }

        public void setCards(List<Double> cards) { this.cards = cards; }

        public Gender getGender() { return gender; }

        public void setGender(Gender gender) { this.gender = gender; }

        @Override
        @CompilerControl(CompilerControl.Mode.DONT_INLINE)
        public boolean equals(Object o) {
            if (this == o) return true;

            if (!(o instanceof ApacheCommons3User)) return false;

            ApacheCommons3User that = (ApacheCommons3User) o;

            return new org.apache.commons.lang3.builder.EqualsBuilder()
                    .append(id, that.id)
                    .append(name, that.name)
                    .append(phones, that.phones)
                    .append(cards, that.cards)
                    .append(gender, that.gender)
                    .isEquals();
        }

        @Override
        @CompilerControl(CompilerControl.Mode.DONT_INLINE)
        public int hashCode() {
            return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                    .append(id)
                    .append(name)
                    .append(phones)
                    .append(cards)
                    .append(gender)
                    .toHashCode();
        }
    }

    static class SimpleUser implements User {
        private int          id;
        private String       name;
        private String[]     phones;
        private List<Double> cards;
        private Gender       gender;

        static SimpleUser of(User user) {
            SimpleUser u = new SimpleUser();
            u.setId(user.getId());
            u.setName(user.getName());
            u.setPhones(Arrays.copyOf(user.getPhones(), user.getPhones().length));
            u.setCards(new ArrayList<>(user.getCards()));
            u.setGender(user.getGender());
            return u;
        }

        public int getId() { return id; }

        public void setId(int id) { this.id = id; }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public String[] getPhones() { return phones; }

        public void setPhones(String[] phones) { this.phones = phones; }

        public List<Double> getCards() { return cards; }

        public void setCards(List<Double> cards) { this.cards = cards; }

        public Gender getGender() { return gender; }

        public void setGender(Gender gender) { this.gender = gender; }

        @Override
        @CompilerControl(CompilerControl.Mode.DONT_INLINE)
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof SimpleUser)) return false;
            SimpleUser simpleUser = (SimpleUser) o;
            if (id != simpleUser.id) return false;
            if (name != null ? !name.equals(simpleUser.name) : simpleUser.name != null) return false;
            // Probably incorrect - comparing Object[] arrays with Arrays.equals
            if (!Arrays.equals(phones, simpleUser.phones)) return false;
            if (cards != null ? !cards.equals(simpleUser.cards) : simpleUser.cards != null) return false;
            return gender == simpleUser.gender;
        }

        @Override
        @CompilerControl(CompilerControl.Mode.DONT_INLINE)
        public int hashCode() {
            int result = id;
            result = 31 * result + (name != null ? name.hashCode() : 0);
            result = 31 * result + Arrays.hashCode(phones);
            result = 31 * result + (cards != null ? cards.hashCode() : 0);
            result = 31 * result + (gender != null ? gender.hashCode() : 0);
            return result;
        }
    }

    static class GuavaUser implements User {
        private int          id;
        private String       name;
        private String[]     phones;
        private List<Double> cards;
        private Gender       gender;

        static GuavaUser of(User user) {
            GuavaUser u = new GuavaUser();
            u.setId(user.getId());
            u.setName(user.getName());
            u.setPhones(Arrays.copyOf(user.getPhones(), user.getPhones().length));
            u.setCards(new ArrayList<>(user.getCards()));
            u.setGender(user.getGender());
            return u;
        }

        public int getId() { return id; }

        public void setId(int id) { this.id = id; }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public String[] getPhones() { return phones; }

        public void setPhones(String[] phones) { this.phones = phones; }

        public List<Double> getCards() { return cards; }

        public void setCards(List<Double> cards) { this.cards = cards; }

        public Gender getGender() { return gender; }

        public void setGender(Gender gender) { this.gender = gender; }

        @Override
        @CompilerControl(CompilerControl.Mode.DONT_INLINE)
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GuavaUser)) return false;
            GuavaUser guavaUser = (GuavaUser) o;
            return id == guavaUser.id &&
                    com.google.common.base.Objects.equal(name, guavaUser.name) &&
                    com.google.common.base.Objects.equal(phones, guavaUser.phones) &&
                    com.google.common.base.Objects.equal(cards, guavaUser.cards) &&
                    gender == guavaUser.gender;
        }

        @Override
        @CompilerControl(CompilerControl.Mode.DONT_INLINE)
        public int hashCode() {
            return com.google.common.base.Objects.hashCode(id, name, phones, cards, gender);
        }
    }

    @Data
    @ToString(doNotUseGetters = true)
    @EqualsAndHashCode(doNotUseGetters = true)
    static class LombokUser implements User {
        private int          id;
        private String       name;
        private String[]     phones;
        private List<Double> cards;
        private Gender       gender;

        static LombokUser of(User user) {
            LombokUser u = new LombokUser();
            u.setId(user.getId());
            u.setName(user.getName());
            u.setPhones(Arrays.copyOf(user.getPhones(), user.getPhones().length));
            u.setCards(new ArrayList<>(user.getCards()));
            u.setGender(user.getGender());
            return u;
        }
    }

    static class Java7User implements User {
        private int          id;
        private String       name;
        private String[]     phones;
        private List<Double> cards;
        private Gender       gender;

        static Java7User of(User user) {
            Java7User u = new Java7User();
            u.setId(user.getId());
            u.setName(user.getName());
            u.setPhones(Arrays.copyOf(user.getPhones(), user.getPhones().length));
            u.setCards(new ArrayList<>(user.getCards()));
            u.setGender(user.getGender());
            return u;
        }

        public int getId() { return id; }

        public void setId(int id) { this.id = id; }

        public String getName() { return name; }

        public void setName(String name) { this.name = name; }

        public String[] getPhones() { return phones; }

        public void setPhones(String[] phones) { this.phones = phones; }

        public List<Double> getCards() { return cards; }

        public void setCards(List<Double> cards) { this.cards = cards; }

        public Gender getGender() { return gender; }

        public void setGender(Gender gender) { this.gender = gender; }

        @Override
        @CompilerControl(CompilerControl.Mode.DONT_INLINE)
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Java7User)) return false;
            Java7User java7User = (Java7User) o;
            return id == java7User.id &&
                    Objects.equals(name, java7User.name) &&
                    Arrays.equals(phones, java7User.phones) &&
                    Objects.equals(cards, java7User.cards) &&
                    gender == java7User.gender;
        }

        @Override
        @CompilerControl(CompilerControl.Mode.DONT_INLINE)
        public int hashCode() {
            return Objects.hash(id, name, phones, cards, gender);
        }
    }
}