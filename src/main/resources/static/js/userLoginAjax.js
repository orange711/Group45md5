//页面的跳转
function go(){
    window.location.href="http://localhost:8080/vacBook/user/1";
}
//登录验证
$("#login").submit(function(){
    //获取用户名和密码
    const name = $("#name").val();//输入的用户名
    const password = $("#password").val();//输入的密码

    //调ajax
    $.ajax({
        url:"http://localhost:8080/vacBook/user/login",
        data:{name:name,password:password},
        type:"POST",
        dataType:"text",
        success: function(data){
            if(data.trim()=="OK")//要加上去空格，防止内容里面有空格引起错误。
            {
                const str = "login success";
                /* 显示提示信息 */
                document.getElementById("login-button").innerHTML = str;
                setTimeout(go, 500);//0.5秒后页面跳转
            }
            else
            {
                const str = "用户名或密码错误！";
                /* 显示提示信息 */
                document.getElementById("login-button").innerHTML = str;
            }
        }
    });

    document.getElementById("loginExit").innerHTML = "";
    return false;
});

// //注册验证
// $("#register").submit(function() {
//     //获取用户名、密码、确认密码和邮箱值
//     var name = $("#name2").val();//输入的用户名
//     var pass = $("#password2").val();//输入的密码
//     var repass = $("#repassword").val();//输入的确认密码
//     var email = $("#email").val();//输入的邮箱
//
//     //调ajax
//     $.ajax({
//         url:"${APP_PATH}/userController/setSignUp",
//         data:{name:name,pass:pass,email:email,repass:repass},
//         type:"POST",
//         dataType:"text",
//         success: function(data){
//             if(data.trim()=="OK")//要加上去空格，防止内容里面有空格引起错误。
//             {
//                 var str = "注册成功";
//                 /* 显示提示信息 */
//                 document.getElementById("registerSuccess").innerHTML = str;
//                 setTimeout(go, 500);//0.5秒后页面跳转
//             }else if (data.trim()=="PASS") {
//                 var str = "两次输入的密码不一致！";
//                 /* 显示提示信息 */
//                 document.getElementById("confirmPassExit").innerHTML = str;
//                 /* 清空指定输入框 */
//                 document.getElementById("password2").value = "";
//                 document.getElementById("repassword").value = "";
//                 /* 将光标定为到指定文本框 */
//                 document.getElementById("password2").focus();
//             }else if (data.trim()=="NO") {
//                 var str = "该用户名已存在！";
//                 /* 显示提示信息 */
//                 document.getElementById("registerName").innerHTML = str;
//             }
//         }
//     });
//
//     /* 清空指定div */
//     document.getElementById("confirmPassExit").innerHTML = "";
//     document.getElementById("registerName").innerHTML = "";
//     return false;
// });