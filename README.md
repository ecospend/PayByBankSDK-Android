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
                                    
     ------Bulk Payment
    /**  Opens webview using with `uniqueID` of the BulkPayment Paylink.
     *
     *@property activity: Activity that provides to present bank selection
     *@property uniqueID: Unique id value of BulkPayment.
     *@property completion: It provides to handle result or error
     */
    fun open(
        activity: Activity,
        uniqueID: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) 

    /**  Opens webview using with request model of the BulkPayment Paylink.
     *
     *@property activity: Activity that provides to present bank selection
     *@property request: Request to create BulkPayment.
     *@property completion: It provides to handle result or error
     */
    fun initiate(
        activity: Activity,
        request: BulkPaymentCreateRequest,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) 

    /**  Creates BulkPayment
     *
     *@property request: Request to create BulkPayment.
     *@property completion: It provides to handle result or error
     */
    fun createBulkPayment(
        request: BulkPaymentCreateRequest,
        completion: (BulkPaymentCreateResponse?, PayByBankError?) -> Unit
    ) 

    /**  Gets BulkPayment detail
     *
     *@property request: Request to get detail of  BulkPayment.
     *@property completion: It provides to handle result or error
     */
    fun getBulkPayment(
        request: BulkPaymentGetRequest,
        completion: (BulkPaymentGetResponse?, PayByBankError?) -> Unit
    ) 
    
    /**  Soft deletes the BulkPayment Paylink with given id.
     *@property request: Request to deactivate BulkPayment.
     *@property completion: It provides to handle result or error
     */
    fun deactivateBulkPayment(
        request: BulkPaymentDeleteRequest,
        completion: (Boolean?, PayByBankError?) -> Unit
    ) 
    
     ------Datalink 
       
     /**
     *  Opens webview using with `uniqueID` of datalink
     *
     *@property activity: Activty that provides to present bank selection
     *@property uniqueID: Unique id value of datalink.
     *@property completion: It provides to handle result or error
     */
    fun open(
        activity: Activity,
        uniqueID: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) 

    /**
     *  Opens webview using with request model of datalink
     *
     *@property activity: Activty that provides to present bank selection
     *@property request: Request to create datalink.
     *@property completion: It provides to handle result or error
     */
    fun initiate(
        activity: Activity,
        request: DatalinkCreateRequest,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) 

    /**
     *   Creates Datalink
     *
     *@property request: Request to create Datalink
     *@property completion: It provides to handle result or error
     */
    fun createDatalink(
        request: DatalinkCreateRequest,
        completion: (DatalinkCreateResponse?, PayByBankError?) -> Unit
    ) 

    /**
     *  Gets Datalink detail
     *
     *@property request: Request to get Datalink detail
     *@property completion: It provides to handle result or error
     */
    fun getDatalink(
        request: DatalinkGetRequest,
        completion: (DatalinkGetResponse?, PayByBankError?) -> Unit
    ) 

    /**
     * Deletes the Datalink with given id.
     *
     *@property request: Request to soft delete Datalink
     *@property completion: It provides to handle result or error
     */
    fun deleteDatalink(
        request: DatalinkDeleteRequest,
        completion: (Boolean?, PayByBankError?) -> Unit
    ) 

    /**
     *  Returns datalink with given `consentID`
     *
     *@property request:  Request to get Datalink of a consent
     *@property completion: It provides to handle result or error
     */
    fun getDatalinkOfConsent(
        request: DatalinkGetConsentDatalinkRequest,
        completion: (DatalinkGetResponse?, PayByBankError?) -> Unit
    )
    
     ------FrPayment
     
     /**
     *  Opens webview using with request model of FrPayment
     *
     *@property activity: Activty that provides to present bank selection
     *@property request: Request to create FrPayment
     *@property completion: It provides to handle result or error
     */
    fun initiate(
        activity: Activity,
        request: FrPaymentCreateRequest,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) 

    /**
     *  Opens webview using with `uniqueID` of FrPayment
     *
     *@property activity: Activty that provides to present bank selection
     *@property uniqueID: Unique id value of FrPayment.
     *@property completion: It provides to handle result or error
     */
    fun open(
        activity: Activity,
        uniqueID: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    )

    /**
     * Creates FrPayment
     *
     *@property request: Request to create FrPayment
     *@property completion: It provides to handle result or error
     */
    fun createFrPayment(
        request: FrPaymentCreateRequest,
        completion: (FrPaymentCreateResponse?, PayByBankError?) -> Unit
    )

    /**
     *  Gets FrPayment detail
     *
     *@property request: Request to get FrPayment detail
     *@property completion: It provides to handle result or error
     */
    fun getFrPayment(
        request: FrPaymentGetRequest,
        completion: (FrPaymentGetResponse?, PayByBankError?) -> Unit
    ) 

    /**
     *  Soft deletes FrPayment with given id
     *
     *@property request: Request to get FrPayment detail
     *@property completion: It provides to handle result or error
     */
    fun deactivateFrPayment(
        request: FrPaymentDeleteRequest,
        completion: (Boolean, PayByBankError?) -> Unit
    )
    
    -----------Paylink 
    
    /**
     *  Opens bank application or bank website using with request model of payment
     *
     *@property activity: Activty that provides to present bank selection
     *@property request: Request to create paylink
     *@property completion: It provides to handle result or error
     */
    fun initiate(
        activity: Activity,
        request: PaylinkCreateRequest,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) 

    /**
     *  Opens webview using with `uniqueID` of paylink
     *
     *@property activity: Activty that provides to present bank selection
     *@property uniqueID:  Unique id value of paylink.
     *@property completion: It provides to handle result or error
     */
    fun open(
        activity: Activity,
        uniqueID: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) {
        execute(
            activity = activity,
            type = PaylinkExecuteType.Open(uniqueID),
            completion = completion
        )
    }

    /**
     * Creates Paylink
     *
     *@property request: Request to create Paylink
     *@property completion: It provides to handle result or error
     */
    fun createPaylink(
        request: PaylinkCreateRequest,
        completion: (PaylinkCreateResponse?, PayByBankError?) -> Unit
    )

    /**
     * Creates Paylink
     *
     *@property request: Request to create Paylink
     *@property completion: It provides to handle result or error
     */
    fun getPaylink(
        request: PaylinkGetRequest,
        completion: (PaylinkGetResponse?, PayByBankError?) -> Unit
    )

    /**
     * Deactivates Paylink
     *
     *@property request: Request to create Paylink
     *@property completion: It provides to handle result or error
     */
    fun deActivatePaylink(
        request: PaylinkDeleteRequest,
        completion: (Boolean, PayByBankError?) -> Unit
    )
    
    
    -------------Vrplink
     /**
     * Opens webview using with request model of VRPlink
     *
     *@property activity: Activty that provides to present bank selection
     *@property request: Request to create VRPlink
     *@property completion: It provides to handle result or error
     */
    fun initiate(
        activity: Activity,
        request: VRPlinkCreateRequest,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) 

    /**
     *  Opens webview using with `uniqueID` of VRPlink
     *
     *@property activity: Activty that provides to present bank selection
     *@property uniqueID: Unique id value of VRPlink.
     *@property completion: It provides to handle result or error
     */
    fun open(
        activity: Activity,
        uniqueID: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    )

    /**
     *  Creates VRPlink
     *
     *@property request: Request to create VRPlink
     *@property completion: It provides to handle result or error
     */
    fun createVRPlink(
        request: VRPlinkCreateRequest,
        completion: (VRPlinkCreateResponse?, PayByBankError?) -> Unit
    ) -

    /**
     *   Gets VRPlink detail
     *
     *@property request: Request to get VRPlink detail
     *@property completion: It provides to handle result or error
     */
    fun getVRPlink(
        request: VRPlinkGetRequest,
        completion: (VRPlinkGetResponse?, PayByBankError?) -> Unit
    ) 

    /**
     *   Soft deletes the VRPlink with given id.
     *
     *@property request: Request to deactivate VRPlink
     *@property completion: It provides to handle result or error
     */
    fun deactivateVRPlink(
        request: VRPlinkDeleteRequest,
        completion: (Boolean?, PayByBankError?) -> Unit
    ) 

    /**
     *  Returns records of VRPlink
     *
     *@property request: Request to get VRPlink records
     *@property completion: It provides to handle result or error
     */
    fun getVRPlinkRecords(
        request: VRPlinkGetRecordsRequest,
        completion: (List<VRPlinkGetRecordsResponse>?, PayByBankError?) -> Unit
    ) 
    
    --------Payment 
    /**
     *  Opens bank application or bank website using with request model of payment
     *
     *@property activity: Activty that provides to present bank selection
     *@property request: Request to create payment
     *@property completion: It provides to handle result or error
     */
    fun initiate(
        activity: Activity,
        request: PaymentCreateRequest,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    )

    /**
     * Opens bank application or bank website using with `id` of payment
     *
     *@property activity: Activty that provides to present bank selection
     *@property uniqueID: Unique id value of payment.
     *@property completion: It provides to handle result or error
     */
    fun open(
        activity: Activity,
        uniqueID: String,
        completion: (PayByBankResult?, PayByBankError?) -> Unit
    ) 

    /**
     *  Opens bank application or bank website using with request model of payment
     *
     *@property request: Request to create payment.
     *@property completion: It provides to handle result or error
     */
    fun createPayment(
        request: PaymentCreateRequest,
        completion: (PaymentCreateResponse?, PayByBankError?) -> Unit
    ) 

    /**
     *  Gets payments.
     *
     *@property request:  Request to list of payments with filters.
     *@property completion: It provides to handle result or error
     */
    fun listPayments(
        request: PaymentListRequest,
        completion: (PaymentListResponse?, PayByBankError?) -> Unit
    ) 

    /**
     *  Gets payment detail.
     *
     *@property request: : Request to get payment detail with id.
     *@property completion: It provides to handle result or error
     */
    fun getPayment(
        request: PaymentGetRequest,
        completion: (PaymentGetResponse?, PayByBankError?) -> Unit
    ) 

    /**
     * Checks availability of payment url.
     *
     * 'url-consumed' endpoint checks whether the bank's payment url has been visited by the PSU.
     * Return's true if the PSU has logged in to the banking system for this payment.
     * In such case either wait for the PSU to finish the journey, or create a new payment.
     *
     *@property request: Request to check availability of payment url.
     *@property completion: It provides to handle result or error
     */
    fun checkPaymentURL(
        request: PaymentCheckURLRequest,
        completion: (PaymentCheckURLResponse?, PayByBankError?) -> Unit
    )

    /**
     *  Creates refund for given payment
     *
     *@property request: Request to create refund for given payment.
     *@property completion: It provides to handle result or error
     */
    fun createRefund(
        request: PaymentCreateRefundRequest,
        completion: (PaymentCreateResponse?, PayByBankError?) -> Unit
    ) 
```


###  Without Authentication

You can open payment or verification urls via OpenWithUrl methods. It is not requeire to set acces token for this operations. 


## Sample Projects

We have provided a sample project in the repository. Source files for these are in the `Examples` directory in the project navigator.

## License

PayByBank SDK is released under the [Apache License](LICENSE).