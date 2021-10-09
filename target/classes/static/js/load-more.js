$( document ).ready(function () {
    $(".moreTable").slice(0, 5).show();

    console.log($(".moreTable").length);

      if ($(".blogBox:hidden").length != 0) {
        $("#loadMore").show();
      }   
      $("#loadMore").on('click', function (e) {
        e.preventDefault();
        //防止访问URL

        $(".moreTable:hidden").slice(0, 5).show();


        if ($(".moreTable:hidden").length == 0) {
          $("#loadMore").fadeOut(1000);
        } // 如果没有更多了，那么fadeout


      });
    });

