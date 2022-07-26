# PayByBank SDK (Android)

The Ecospend Gateway presents PayByBank SDK as an alternative and easier form of Open Banking Instant Payment solutions. PayByBank SDK provides you the option of downsizing the development effort for a PIS and AIS journeys to a single SDK integration. PayByBank undertakes all of interaction in the payment user journey with your branding on display.

- `Paylink` provides to execute the payment order.
- `FrPayment` provides to execute a standing order.
- `Bulk Payment` provides to execute the Bulk Payment order.
- `VRPlink` provides to execute the Variable Recurring Payments consent.
- `Datalink` is a whitelabel consent journey solution provided by Ecospend that downsizes the required implementation for the consent journey to a single endpoint integration.
- `Payment` provides to execute the domestic instant payments, international payments, and scheduled payments.


## Installation

### Gradle

Each project contains one top-level Gradle build file. This file is named build.gradle and can be found in the top level directory. This file usually contains common config for all modules, common functions..

All modules have a specific build.gradle file. This file contains all info about this module (because a project can contain more modules), as config,build tyoes, info for signing your apk, dependencies

Any new dependency that you want to add to your application or module should go under build.gradle(Module app) and to add the dependency just write the compile statement that is given on the github page..

```
sourceControl {
    gitRepository("https://github.com/ecospend/PayByBank-Android.git") {
        producesModule("com.ecospend.paybybanksdk-android:paylinkmobilesdk-android")
    }
}

dependencies {
    ...
    
    implementation 'com.ecospend.paybybanksdk-android:v1.0'
}

```

Then sync project with gradle files

In any file you'd like to use PayByBank in, don't forget to import the framework with `import com.ecospend.paylinksdk.app.PayByBank`


### Usage

*Note: Please look at <https://docs.ecospend.com/api/intro> for more details.*

To start using our API, you need to onboard with us and get a Client Id (`client_id`) and Client Secret (`client_secret`) via email to <support@ecospend.com>. For onboarding we will need the following information:

- The full name of your company/organization
- An email address for your admin user (used as username)
- A mobile phone number for the admin user (used for two-factor authentication)

Once onboarded, a Client Id is generated for you and you will have access to our Management Console, through which you can generate your Client Secret(s).

- The `client_id` is created by Ecospend when your organization is registered with us.
- The `client_secret` is a security key that your administrator should create from the Management Console. This is not visible to or accessible  by the Ecospend team. Therefore, you should store it safely.

You will be given separate pairs of Client Id and Client Secret for our `Sandbox` and `Production` environments respectively. Ecospend does not store these parameters; therefore, you need to keep them safe and secure.

- `Sandbox` environment should be used for testing purposes.
- `Production` environment should be used for released applications.

`PayByBank.configure` function should be called to access `client_id` and `client_secret` before using APIs of PayByBank SDK:
```
PayByBank.configure(environment: <environment>, clientID: <client_id>, clientSecret: <client_secret>)
```

### Sample Projects

We have provided two sample projects in the repository. Source files for these are in the `Examples` directory in project navigator. 
