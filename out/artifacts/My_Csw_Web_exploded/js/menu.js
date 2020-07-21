$(function(){
    var $subblock = $(".subpage-self"), $head=$subblock.find('h2'), $ul = $("#proinfo"), $lis = $ul.find("li"), inter=false;

    $head.click(function(e){
        e.stopPropagation();
        if(!inter){
            $ul.show();
        }else{
            $ul.hide();
        }
        inter=!inter;
    });

    $ul.click(function(event){
        event.stopPropagation();
    });

    $(document).click(function(){
        $ul.hide();
        inter=!inter;
    });

    $lis.hover(function(){
        if(!$(this).hasClass('nochild')){
            $(this).addClass("prosahover");
            $(this).find(".prosmore-self").removeClass('hide');
        }
    },function(){
        if(!$(this).hasClass('nochild')){
            if($(this).hasClass("prosahover")){
                $(this).removeClass("prosahover");
            }
            $(this).find(".prosmore-self").addClass('hide');
        }
    });

});