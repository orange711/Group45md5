
//初始化booking相关参数
var userId = '';
var vaccineId = '';
var bookingTimezone = '';
var date = '';
/**
 *
 * @param t 列表的下标值
 */
function check(t){
    userId = $("#userId").val();
    let provider = $($("td[name='provider']")[t]).text();
    let brand = $($("select[name='brand'] option:selected")[t]).text();
    vaccineId = $($("select[name='brand'] option:selected")[t]).val();
    date = $($("input[name='date']")[t]).val();
    bookingTimezone = $($("input[name='time']")[t]).val();
    $("#confirm_provider").text(provider);
    $("#confirm_brand").text(brand);
    $("#confirm_date").text(date+' '+bookingTimezone);
    $("#confirm_provider1").text(provider);
    $("#confirm_brand1").text(brand);
    $("#confirm_date1").text(date+' '+bookingTimezone);
    let tt = $("#confirm_content").html();
    layer.open({
        type: 0 , //选择默认Layer 0（信息框，默认）
        title: 'CONFIRM YOUR BOOKING',
        shade: 0.4,
        content: tt , //获取DOM元素
        btn: ['Confirm', 'Cancel'], //按钮组
        scrollbar: false , //屏蔽浏览器滚动条
        yes: function(index){    //点击确定回调
            layer.close(index);
            saveBooking();     //调用保存方法
        },
        btn2: function(){      // 点击Cancel回调
            layer.closeAll();
        }
    })
    // }
}
function saveBooking(){
    var data = {"userId":userId,"vaccineId":vaccineId,"date":date,"bookingTimezone":bookingTimezone};
    $.ajax({
        url: "/vacbook/booking/fetch/",
        data: data,
        type: "post",
        success:function (data){
            if(data==true){
                let tt = $("#book-info").html(); //用于弹窗的代码部分
                sendConfirmEmail(userId, vaccineId, date, bookingTimezone);
                layer.open({
                    type: 0 ,  //选择默认Layer 0（信息框，默认）
                    title: 'Thanks for your booking',   //标题
                    shade: 0.4,
                    content: tt ,
                    btn: ['View your Booking','Back to the home page'], //按钮设置
                    scrollbar: false ,
                    yes: function(){  //点击确定回调
                        window.location.href ='/vacbook/booking/user/'+userId;
                    },
                    btn2: function(index){      // 点击Cancel回调
                        layer.close(index);
                    },
                })
            }else {
                layer.confirm('Sorry, you have already booked the vaccine and cannot book again:)', {
                    btn: ["OK!"],
                    icon: 2,
                    title: "Booking confirm!"
                }, function () {
                    //点击确后关闭提示框
                    layer.closeAll('dialog');
                });
            }
        }
    });
}

function sendConfirmEmail(userId, vaccineId, date, bookingTimezone){
    var data = {
        "userId": userId,
        "vaccineId":vaccineId,
        "date": date,
        "bookingTimezone":bookingTimezone,
    }
    $.ajax({
        url: "/vacbook/booking/sendConfirmEmail/",
        data: data,
        type: "post",
        dataType: "json",
    });

}