package io.wakelesstuna;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class PasswordGenerator {

    private final int minimumSizeForPassword;
    private final String LOWERCASE;
    private final String UPPERCASE;
    private final String DIGITS;
    private final String SYMBOLS;
    private final boolean useLowerCase;
    private final boolean useUpperCase;
    private final boolean useDigits;
    private final boolean useSymbols;

    private PasswordGenerator() {
        throw new UnsupportedOperationException("Empty constructor is not supported.");
    }

    private PasswordGenerator(PasswordGeneratorBuilder builder) {
        this.minimumSizeForPassword = builder.minimumSizeForPassword;
        this.useLowerCase = builder.useLowerCase;
        this.useUpperCase = builder.useUpperCase;
        this.useDigits = builder.useDigits;
        this.useSymbols = builder.useSymbols;
        LOWERCASE = builder.lowerCase;
        UPPERCASE = builder.upperCase;
        DIGITS = builder.digits;
        SYMBOLS = builder.symbols;
    }

    public static class PasswordGeneratorBuilder {
        private int minimumSizeForPassword;
        private String lowerCase;
        private String upperCase;
        private String digits;
        private String symbols;
        private boolean useLowerCase;
        private boolean useUpperCase;
        private boolean useDigits;
        private boolean useSymbols;

        public PasswordGeneratorBuilder() {
            this.minimumSizeForPassword = 4;
            this.lowerCase = "abcdefghijklmnopqrstuvwxyz";
            this.upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            this.digits = "0123456789";
            this.symbols = "!@#$%&*()_+-=[]|,./?><";
            this.useLowerCase = false;
            this.useUpperCase = false;
            this.useDigits = false;
            this.useSymbols = false;
        }

        /**
         * Set true in case you would like to include lower characters
         * (abc...xyz). Default false.
         *
         * @param useLowerCase true in case you would like to include lower
         * characters (abc...xyz). Default false.
         * @return the builder for chaining.
         */
        public PasswordGeneratorBuilder useLowerCase(boolean useLowerCase) {
            this.useLowerCase = useLowerCase;
            return this;
        }

        /**
         * Set true in case you would like to include upper characters
         * (ABC...XYZ). Default false.
         *
         * @param useUpperCase true in case you would like to include upper
         * characters (ABC...XYZ). Default false.
         * @return the builder for chaining.
         */
        public PasswordGeneratorBuilder useUpperCase(boolean useUpperCase) {
            this.useUpperCase = useUpperCase;
            return this;
        }

        /**
         * Set true in case you would like to include digit characters (123..).
         * Default false.
         *
         * @param useDigits true in case you would like to include digit
         * characters (123..). Default false.
         * @return the builder for chaining.
         */
        public PasswordGeneratorBuilder useDigits(boolean useDigits) {
            this.useDigits = useDigits;
            return this;
        }

        /**
         * Set true in case you would like to include symbol characters
         * (!@#..). Default false.
         *
         * @param useSymbols true in case you would like to include
         * symbol characters (!@#..). Default false.
         * @return the builder for chaining.
         */
        public PasswordGeneratorBuilder useSymbols(boolean useSymbols) {
            this.useSymbols = useSymbols;
            return this;
        }

        /**
         * To change the password length.
         *
         * @param passwordLength how long you want the password to be.
         * Default 4.
         * @return the builder for chaining.
         */
        public PasswordGeneratorBuilder customPasswordLength(int passwordLength) {
            if (passwordLength <= 1) {
                throw new IllegalArgumentException("The password length must be at least 2 characters long");
            }
            this.minimumSizeForPassword = passwordLength;
            return this;
        }

        /**
         * To add custom lowercase letter for generate the password.
         *
         * @param lowerCase witch lowercase letters you wanna use.
         * Default a-z.
         * @return the builder for chaining.
         */
        public PasswordGeneratorBuilder customLowerCase(String lowerCase){
            if (!lowerCase.matches("^[a-z]+$")) {
                throw new UnsupportedOperationException("Only lowercase letters are allowed as input");
            }
            this.lowerCase = lowerCase;
            return this;
        }

        /**
         * To add custom uppercase letter for generate the password.
         *
         * @param upperCase witch uppercase letters you want use.
         * Default A-Z.
         * @return the builder for chaining.
         */
        public PasswordGeneratorBuilder customUpperCase(String upperCase){
            if (!upperCase.matches("^[A-Z]+$")) {
                throw new UnsupportedOperationException("Only uppercase letters are allowed as input");
            }
            this.upperCase = upperCase;
            return this;
        }

        /**
         * To add custom digits for generate the password
         *
         * @param digits witch digits you want use.
         * Default 0-9.
         * @return the builder for chaining.
         */
        public PasswordGeneratorBuilder customDigits(String digits){
            if (!digits.matches("^[0-9]+$")) {
                throw new UnsupportedOperationException("Only digits are allowed as input");
            }
            this.digits = digits;
            return this;
        }

        /**
         * To add custom symbols for generate the password.
         *
         * @param symbols witch symbols letters you want use
         * Default !@#$%&*()_+-=[]|,./?><.
         * @return the builder for chaining.
         */
        public PasswordGeneratorBuilder customSymbols(String symbols){
            if (symbols.matches("^[a-zA-Z0-9]+$")){
                throw new UnsupportedOperationException("Only symbols are allowed as input");
            }
            this.symbols = symbols;
            return this;
        }

        /**
         * Get an object to use.
         *
         * @return PasswordGenerator object.
         */
        public PasswordGenerator build() {
            return new PasswordGenerator(this);
        }
    }

    /**
     * This method will generate a password depending the use* properties you
     * define. It will use the rules and generate a password.
     *
     * @param length the length of the password you would like to generate.
     * @return a password that uses the categories you define when constructing
     * the PasswordGenerator object.
     */
    public String generate(int length) {
        if (length <= minimumSizeForPassword) {
            throw new UnsupportedOperationException("Password must be at least ");
        }

        List<String> rules = new ArrayList<>(4);
        if (useLowerCase) {
            rules.add(LOWERCASE);
        }
        if (useUpperCase) {
            rules.add(UPPERCASE);
        }
        if (useDigits) {
            rules.add(DIGITS);
        }
        if (useSymbols) {
            rules.add(SYMBOLS);
        }

        return buildPassword(rules, length);
    }

    /**
     * Builds the password of the given password length and rules.
     *
     * @param rules the rules you wanna use for the password.
     * @param length the length of the password you would like to generate.
     * @return a password as a String.
     */
    private String buildPassword(List<String> rules, int length) {
        StringBuilder password = new StringBuilder(length);
        Random random = new SecureRandom();
        String charRule;
        int position;
        for (int i = 0; i < length; i++) {
            charRule = rules.get(random.nextInt(rules.size()));
            position = random.nextInt(charRule.length());
            password.append(charRule.charAt(position));
        }

        for (int i = 0; i < rules.size(); i++) {
            charRule = rules.get(i);
            position = random.nextInt(charRule.length());
            password.append(charRule.charAt(position));
        }
        return new String(password);
    }
}
