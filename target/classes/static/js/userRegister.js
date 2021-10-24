function check() {
    let firstName = document.getElementById("firstname").value;
    let age = document.getElementById("age").value;
    let lastName = document.getElementById("lastname").value;
    let password = document.getElementById("password").value;
    let phone = document.getElementById("phoneNumber").value;
    let passwordSecond = document.getElementById("passwordSecond").value;
    let gender = document.getElementById("gender").value;
    let address = document.getElementById("autocomplete").value;
    let email = document.getElementById("email").value;
    let account = document.getElementById("account").value;

    console.log();

    let key = document.getElementById("questionAnswer").value



    if (password != passwordSecond) {
        layer.msg("Different password.");
        return false;
    }

    if (firstName.trim() == null || firstName == "") {
        layer.msg("First name can't be empty");
        return false;
    }

    if (age == null || age == "") {
        layer.msg("Age can't be empty");
        return false;
    }
    if (age<=0||age>120) {
        layer.msg("Age is wrong.");
        return false;
    }

    if (lastName.trim() == null || lastName == "") {

        layer.msg("Last name can't be empty");
        return false;
    }



    if (password == null || password == "") {
        layer.msg("Password can't be empty");
        return false;
    }

    if (account== null || account== "") {
        layer.msg("Account can't be empty");
        return false;
    }

    if (passwordSecond == null || passwordSecond == "") {
        layer.msg("Password confirm can't be empty");
        return false;
    }

    if (email.trim() == null || email == "") {
        layer.msg("email can't be empty");
        return false;
    }

    if (key == null || key == "") {
        layer.msg("Answer can't be empty");
        return false;
    }

    if ( address == "") {
        layer.msg("Please select your address!")
        return false;
    }

    if (gender == "Select" || gender == "") {
        layer.msg("Please select your gender!")
        return false;
    }


    if (phone.trim() == null || phone == "") {
        layer.msg("phone can't be empty");
        return false;
    }

    if(phone>9999999999){
        layer.msg("You phone number is wrong");
        return false;
    }





    var question = document.getElementById("question").value;


    if (password == passwordSecond) {
        var data = {
            "userLastname": lastName,
            "userFirstname": firstName,
            "phoneNumber": phone,
            "email": email,
            "gender": gender,
            "address": address,
            "age": age,
            "userPassword": password,
            "userAccount": account,
            "userSafeKey": key,
            "userQuestion": question,
        }
        $.ajax({
            url: "/vacBook/user/register/",
            data: data,
            type: "post",
            dataType: "text",



            success: function(data){
                var code = data.code;
                console.log(code);
                console.log(data.status+"2");
                layer.msg("Congratulations on your registration, jump in one second." );
                setTimeout(function(){//两秒后跳转
                    location.href = "index";
                },2000);
            },
            error : function() {
                console.log(data.status+"1");
                layer.msg("Your account or email or phone has been registered！",{icon: 5});
            }

        });


        return true;
    }
}