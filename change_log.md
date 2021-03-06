
# Nexmo Verify SDK Change Log

## verifyBeta0.2
* getUserStatus is added to retrieve real-time user status. It enables you to get the user status even if a verification is still in progress.
  Usage:
  ```java
	myVerifyClient.isUserVerified(myCountryCode, myPhoneNo, new SearchListener() {
		@Override
		public void onUserStatus(final UserStatus userStatus) {
		    // Update the application UI here if needed.
		}
		@Override
		public void onError(final com.nexmo.sdk.verify.event.VerifyError errorCode, final String errorMessage) {
		    // Update the application UI here if needed.
		}
		@Override
		public void onException(IOException exception) {
		    // Update the application UI here if needed. Most probably there is a network connectivity exception.
		}
         }
```

* commands are implemented: CANCEL/LOGOUT/TRIGGER_NEXT_EVENT
  Usage:
   ```java
	myVerifyClient.command(myCountryCode, myPhoneNo, Command.LOGOUT, new CommandListener() {
		@Override
		public void onSuccess(Command command) {
		    // Update the application UI here if needed.
		}
		@Override
		public void onError(final com.nexmo.sdk.verify.event.VerifyError errorCode, final String errorMessage) {
		    // Update the application UI here if needed.
		}
		@Override
		public void onException(IOException exception) {
		    // Update the application UI here if needed. Most probably there is a network connectivity exception.
		}
        }
```

* Sample application provided: VerifySample is available.

* added IP address support for cellular networks as well, unless it's the loopback address.

* deprecated VerifyClient#getVerifiedUser() that allowed automatic phone number retrieve, as TelephonyManager is not always reliable in geeting a valid phone number.

* added network exception callback for each request. Callback: onException(IOException exception)

* phone number supplied is not checked anymore against the SIM's card phone number, as the method is not reliable, especially on CDMA phones or ported numbers. Therefore, the SDK can now be used even when the user does not supply the same phone number as the one on the device/handset.

## verifyBeta0.1
**May 4 2015**
* Initial version
