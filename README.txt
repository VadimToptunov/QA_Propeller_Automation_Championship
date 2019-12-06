The code is implemented for Propeller QA Automation Championship by Vadim Toptunov.

No logging implemented.

The code can be run on Jenkins with the following commands:

    cd %ProjectFolder%/QA_Propeller_Battle/
    mvn clear test

Bugs found:
    Login page:
         1. Log In page title is incorrect - Welcom to Propeller Automated Testing Championship
            It should comply with <h4>Welcome to Propeller Championship!</h4>
         2. If a user tries to log in with a wrong login/password pair it is not redirected to Error page.
         3. Login is thought to be an e-mail, but it easily gets a string like "test".
         4. If a user inserts credentials and then deletes them, Sign In button is still shown.

    Main page:
         1.If a user adds an article to saved, the item disappears from menu.

    Profile page:
          1. If a user inserts no First Name and no Last name, there's only one error message ("Please set your first name.")
             instead of a separate error message for both fields.
          2. If a user sets no payment data, i.e. no card number and no payment system, but tries to save it,
             there's only one error message (Please set your card number), instead of a separate error message for both fields.
          3. Card number accepts all kinds of input, even the invalid one, like ""@##$@$". It should not."
