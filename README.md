# PayByBank SDK (Android)
[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

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

## Usage

*Note: Please look at [API Specifications & Developer's Guide](https://docs.ecospend.com/api/intro) for more details.*

### Onboarding

To start using our API, you need to onboard with us and get a Client Id (`client_id`) and Client Secret (`client_secret`) via email to <support@ecospend.com>. For onboarding we will need the following information:

- The full name of your company/organization
- An email address for your admin user (used as username)
- A mobile phone number for the admin user (used for two-factor authentication)

Once onboarded, a Client Id is generated for you and you will have access to our Management Console, through which you can generate your Client Secret(s).

- The `access_token` is required for all subsequent requests to the API. You should keep it safe and secure during its lifetime. The lifetime is configurable.

You will be given separate pairs of Client Id and Client Secret for our `Sandbox` and `Production` environments respectively. Ecospend does not store these parameters; therefore, you need to keep them safe and secure.

- `Sandbox` environment should be used for testing purposes.
- `Production` environment should be used for released applications.
- 
###  Without Authentication

You can open payment or verification urls via OpenWithUrl methods. It is not requeire to set acces token for this operations.
```
    ------Bulk Payment 
    /**
     *  Opens webview using with `url` of BulkPayment
     *
     *@property activity: Activty that provides to present bank selection
     *@property bulkPaymentUrl:  Url value of bulk payment.
     *@property redirectUrl:  Redirect url of bulk payment.
     *@property completion: It provides to handle result or error
     */
    fun openUrl(
        activity: Activity,
        bulkPaymentUrl: String,
        redirectUrl: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    )
    
    
    
    ------Datalink
     /**
     *  Opens webview using with `url` of Datalink
     *
     *@property activity: Activty that provides to present bank selection
     *@property datalinkUrl:  Url of paylink.
     *@property redirectUrl:  Redirect url value of paylink.
     *@property completion: It provides to handle result or error
     */
    fun openUrl(
        activity: Activity,
        datalinkUrl: String,
        redirectUrl: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    )
    
    ----------FrPayment
    /**
     *  Opens webview using with `url` of Frplink
     *
     *@property activity: Activty that provides to present bank selection
     *@property frpUrl:  Url of fr payment.
     *@property redirectUrl:  Redirect url of fr payment.
     *@property completion: It provides to handle result or error
     */
    fun openUrl(
        activity: Activity,
        frpUrl: String,
        redirectUrl: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) 
    
    -----Paylink
     /**
     *  Opens webview using with `url` of paylink
     *
     *@property activity: Activty that provides to present bank selection
     *@property paylinkUrl:  Url of paylink.
     *@property redirectUrl:  Redirect url of paylink.
     *@property completion: It provides to handle result or error
     */
    fun openUrl(
        activity: Activity,
        paylinkUrl: String,
        redirectUrl: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    )
    
    --------VrpLink
     /**
     *  Opens webview using with `url` of vrpLink
     *
     *@property activity: Activty that provides to present bank selection
     *@property vrpUrl:  Vrp of vrpLink.
     *@property redirectUrl:  Redirect url of vrpLink.
     *@property completion: It provides to handle result or error
     */
    fun openUrl(
        activity: Activity,
        vrpUrl: String,
        redirectUrl: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    )
    
```
### Authentication

PayByBank SDK supports [Client Credentials Flow](https://en.wikipedia.org/wiki/OAuth) and [Token-Based Authentication](https://en.wikipedia.org/wiki/Access_token) to access Ecospend Gateway APIs.

- Client Credentials Flow: `PayByBank.configure` function should be called once to access `client_id` and `client_secret` before using APIs of PayByBank SDK.

- Token-Based Authentication: `PayByBank.configure` function should be called to access `access_token` before using APIs of PayByBank SDK. When `access_token` is expired, `PayByBank.configure` function should be called again. To generate `access_token`, check out the [Get Access Token](https://docs.ecospend.com/api/intro/#tag/Get-Access-Token) documentation.

```

PayByBank.configure(environment: <environment>, 
                    token: .token(<access_token>))
```
###  Without Authentication

You can open payment or verification urls via OpenWithUrl methods. It is not requeire to set acces token for this operations. 


## Sample Projects

We have provided a sample project in the repository. Source files for these are in the `Examples` directory in the project navigator.

## License

PayByBank SDK is released under the [Apache License](LICENSE).