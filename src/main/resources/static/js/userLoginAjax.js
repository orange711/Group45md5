function login() {
    //获取用户名和密码
    const name = $("#account").val();//输入的用户名
    const password = $("#password").val();//输入的密码
    console.log(name);
    console.log(password)
    var data = {userAccount: name, userPassword: password}
    //调ajax
    $.ajax({
        url: "http://localhost:8080/vacBook/user/login",
        data: data,
        type: "POST",
        dataType: "text",
        success: function (data) {
            console.log(typeof data)
            if(data === "true"){
                console.log(data + "200");
                layer.msg("Login success!" );
                setTimeout(function () {//两秒后跳转
                    location.href = "index/booking";
                }, 2000);
            }else{
                console.log(data + "240");
                layer.msg("Your account or password is wrong！",{icon: 5});
                return false;
            }
        },
        error: function () {
            location.href = "login";
        }
    });
    return true;
}
