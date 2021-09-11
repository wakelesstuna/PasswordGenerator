# PasswordGenerator 

This is a password generator that generates a random number of your configuration settings.
It uses the `builder pattern` so you need to call the static constructor to configure 
the generator.

example implementation

```
PasswordGenerator pass = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLowerCase(true)
                .useUpperCase(true)
                .useSymbols(true)
                .setMiniumSizeForPassword(16)
                .build();

        String generatedPassword = pass.generate(16);
```

`useDigits() defalut false` 

Sets if you want to use digits in tou password

`useLowerCase() defalut true`

Sets if you want to use digits in tou password

`useUpperCase() defalut false`

Sets if you want to use digits in tou password

`useSymbols() defalut false`

Sets if you want to use digits in tou password

`setMiniumSizeForPassword() defalut 4 minimum 2`

Sets if you want to use digits in tou password