/**
 * js for the adminBookingModel
 */
function cal_booking(btn,id,userId) {
    layer.confirm('Are you sure you want to cancel this Booking now <br> And send reminder email?', {
        btn: ["Confirm", "Cancel"],
        icon: 2,
        title: "Delete Booking Warning!"
    }, function () {
        //点击确后关闭提示框
        layer.closeAll('dialog');

        /*$.get("/vacbook/booking/reject/"+id, function(result){
            if(result){
                $(btn).parent().parent().remove()
                layer.msg('The booking has been deleted !')
            }
        });*/
        $.ajax({
            url: "/vacbook/booking/reject/"+id,
            type: "get",
            beforeSend : sendCancelEmail(id),
            success:function (result){
                if(result){
                    window.location.href = userId;

                }
            },
        });
    });
}

function sendCancelEmail(id){
    var data = {
        "booking_id": id,
    }
    $.ajax({
        url: "/vacbook/booking/sendCancelEmail/",
        data: data,
        type: "post",
        dataType: "json",
    });

}

function saveMyBooking(){
    let userID = document.getElementById("userID").value;
    let date = document.getElementById("date").value;
    let time = document.getElementById("time").value;
    console.log(date);
    console.log(time);
        var data = {
            "date": date,
            "time":time,
            "userID":userID,
        }
        $.ajax({
            url: "/vacbook/booking/update/",
            data: data,
            type: "post",
            dataType: "json",
        });
        layer.msg("change successfully")

        return true;

}
