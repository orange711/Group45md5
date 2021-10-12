function obtain(){
    let mylist = document.getElementById("selectList");
    const optionId = mylist.options[mylist.selectedIndex].id;
    console.log(optionId);
    document.getElementById( optionId).name = "location";
    document.getElementById("option").value = optionId;
}
function check(){
    let name = document.getElementById("name").value;
    let location = document.getElementById("option").value;
    let change = document.getElementById("confirmPassword").value;
    let confirm = document.getElementById("changePassword").value;
    console.log(change);
    console.log(confirm);
    console.log(name);
    console.log(location);
    if(confirm !== change){
        layer.msg("the confirm password is not as sames as the change password")
        return false;
    }

    if(confirm == change){
        var data = {
            "name": name,
            "password":confirm,
            "location":location,
        }
        var msg = data.msg;
        $.ajax({
            url: "/vacbook/admin/setting/",
            data: data,
            type: "post",
            dataType: "json",
        });
        layer.msg("change successfully")

        return true;
    }
}