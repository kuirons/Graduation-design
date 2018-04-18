/**
 * Created by wait on 2016/1/4.
 */
var batchSign = "";
var asyncKey = 1;
function actionState(data, failId, successId) {
    batchSign = data.batchSign;
    asyncKey = data.asyncKey;
    var progress = "0%";
    var intervalId = 0;
    var timer = 0;
    var isFail = 0;

    function getProgress() {
        $.ajax({
            type: "POST",
            url: "actionState?batchSign=" + batchSign + "&asyncKey=" + asyncKey,
            global: false,
            success: function (res) {
                if (res.result == "true") {
                    progress = res.percentage;
                    $("#bar").css("width", progress);
                    $("#num").empty().html(progress);
                    isFail = 0;
                } else {
                    isFail = 1;
                    bootstrapAlert("错误");
                }
            }
        });
        timer++;
        if (progress == "100%" || timer == 100 || isFail == 1) {
            window.clearInterval(intervalId);
            $('#myModal').modal("hide");
            isFail = 0;
            getResult();
        }
        function getResult() {
            $.post("actionResult?batchSign=" + batchSign + "&asyncKey=" + asyncKey, function (data) {
                if (data.result == "true") {
                    var success = data.successList;
                    var fail = data.failList;
                    var title = ["区服", "执行结果"];
                    var column = ["serverName", "result"];
                    var successTable = createTable(success, title, column);
                    var failTable = createTable(fail, title, column);
                    var successDiv = "<button id='successList' class='btn' style='margin-bottom:10px'>成功列表</button><div id='successTable'>" + successTable + "</div>";
                    var failDiv = "<button id='failList' class='btn' style='margin-bottom:10px'>失败列表</button><div id='failTable'>" +
                        failTable + "</div>";
                    $("#" + successId).empty().html(successDiv);
                    $("#" + failId).empty().html(failDiv);
                } else {
                    bootstrapAlert("错误");
                }
                $("#bar").css("width", "0");
                $("#num").empty().html("0%");
            });
        }
    }

    $('#myModal').modal({
        backdrop: 'static'
    });
    intervalId = window.setInterval(function () {
        getProgress();
    }, 2000);
}

$("#successList").live("click", function () {
    $("#successTable").slideToggle();
});
$("#failList").live("click", function () {
    $("#failTable").slideToggle();
});