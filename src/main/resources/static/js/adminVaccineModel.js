/**
 * js for the adminVaccineModel
 */
// $(document).on("click",function (){
//     // $('.table.eBtn').on('click',function (event){
//     //     event.propertyIsEnumerable();
//     //     var href = $(this).attr('href');
//     //     $.get(href,function (l,status){
//     //         $('.editForm #editModal').val(l.stock);
//     //     });
//     //     $('.editForm #editModal').modal();
//     // })
// });

$(document).on("click", ".editModal", function () {
    var myVaccineId = $(this).data('id');
    $(".modal-body #stock").val( myVaccineId );
    // As pointed out in comments,
    // it is unnecessary to have to manually call the modal.
    // $('#addBookDialog').modal('show');
});
