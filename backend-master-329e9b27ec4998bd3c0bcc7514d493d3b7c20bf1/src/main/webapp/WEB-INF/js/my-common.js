function bootstrapValidation(form, data) {

    "use strict";
    var errors = {
        fieldErrors: data.fieldErrors,
        errors: data.actionErrors
    };
    // Clear existing errors on submit
    form.find("div.error").removeClass("error");
    form.find("span.s2_help_inline").remove();
    form.find("div.s2_validation_errors").remove();

    //Handle non field errors
    if (errors.errors && errors.errors.length > 0) {
        var errorDiv = $("<div class='alert alert-error s2_validation_errors'></div>");
        form.prepend(errorDiv);
        $.each(errors.errors, function (index, value) {
            errorDiv.append('<p>' + value + '</p>\n');
        });
    }

    //Handle field errors
    if (errors.fieldErrors) {
        $.each(errors.fieldErrors, function (index, value) {
            var element = form.find(":input[name=\"" + index + "\"]"), controlGroup, controls;
            if (element && element.length > 0) {

                // select first element
                element = $(element[0]);
                controlGroup = element.closest("div.control-group");
                controlGroup.addClass('error');
                controls = controlGroup.find("div.controls");
                if (controls) {
                    controls.append("<span class='help-inline s2_help_inline'>" + value[0] + "</span>");
                }
            }
        });
    }
}
function bootstrapConfirm(message, callback) {
    var div = "<div style='width:300px;margin:0 0 0 0;left:40%;top:30%;' class='modal hide' id='confirmModal'><div class='modal-body'><span><div style='text-align:center; min-height:30px; _height:30px; padding-top:20px'>" + message + "</div></span></div><div style='padding:7px;' class='modal-footer'><a href='javascript:void(0);' id='yes' class='btn'>确定</a><a href='javascript:void(0);' class='btn' id='no'>取消</a></div></div>";
    $("body").append(div);
    $("#confirmModal").modal({
        backdrop: 'static'
    });
    $("#no").click(function () {
        $('#confirmModal').modal('hide');
        $('#confirmModal').remove();
    });
    $("#yes").click(function () {
        $('#confirmModal').modal('hide');
        $('#confirmModal').remove();
        callback();
    });
}
function bootstrapAlert(message) {
    var div = "<div style='width:300px;margin:0 0 0 0;left:40%;top:30%;' class='modal hide' id='confirmModal'><div class='modal-body'><span><div style='text-align:center; min-height:30px; _height:30px; padding-top:20px'>" + message + "</div></span></div><div style='padding:7px;' class='modal-footer'><a href='javascript:void(0);' class='btn' id='no'>确定</a></div></div>";
    $("body").append(div);
    $("#confirmModal").modal({
        backdrop: 'static'
    });
    $("#no").click(function () {
        $('#confirmModal').modal('hide');
        $('#confirmModal').remove();
    });
}
function addFixTableHeadElement(id) {
    var script = "<script type='text/javascript'>   "
        + "	 $(function(){  "
        + "		$.getScript('/js/domresize.js' ,function(){  "
        + "			$.getScript('/js/fixedtableheader.js' ,function(){  "
        + "	      		$('#" + id + "').fixedtableheader(true) "
        + "			});	  "
        + "		});	  "
        + "	});  "
        + "</script>  ";
    return script;
}
function createOperationalTable(data, title, colnum) {
    if (title.length != colnum.length) {
        return "";
    } else {
        var tableid = "fixheadtable" + new Date().getTime();
        var table = "  <table id='" + tableid + "' style='table-layout:fixed;word-wrap:break-word;' class='table table-bordered  table-striped table-hover' ><thead><tr>";
        for (var i = 0; i < title.length; i++) {
            table = table + "<th style='text-align:center'>" + title[i] + "</th>";
        }
        table += "</tr></thead><tbody>";
        for (var i = 0; i < data.length; i++) {
            table += "<tr>";
            var res = data[i];
            for (var j = 0; j < colnum.length; j++) {
                if (typeof(colnum[j]) == "object") {

                    table += "<td style='text-align:center'>" + colnum[j].value + "</td>";
                } else {
                    var value = typeof(res[colnum[j]]) == "undefined" ? "" : res[colnum[j]];
                    table += "<td name=" + colnum[j] + " style='text-align:center'>" + value + "</td>";
                }

            }
            table += "</tr>";
        }
        if (data.length == 0) {
            table += "<tr> <td colspan=" + title.length + ">Empty !</td></tr>";
        }
        table += "</tbody></table>";
        return table + addFixTableHeadElement(tableid);
    }
}

function in_array(array, e) {
    for (var i = 0; i < array.length; i++) {
        if (array[i] == e)
            return true;
    }
    return false;
};

function createCommonTable(data) {
    var table;
    var attrs = new Array();
    for (var i = 0; i < data.length; i++) {
        for (var attr in data[i]) {
            //alert(attr);
            if (!in_array(attrs, attr)) {
                attrs.push(attr);
            }
        }
    }
    var tableid = "fixheadtable" + new Date().getTime();
    table = "<table id='" + tableid + "' " +
        "style='table-layout:fixed;word-wrap:break-word;' " +
        "class='table" +
        " table-bordered " +
        " table-striped table-hover" +
        " ' " +
        "><thead><tr class='info'>";
    for (var i = 0; i < attrs.length; i++) {
        table = table + "<th>" + attrs[i] + "</th>";
    }
//	for(var attr in attrs){
//		table = table+"<th>"+attr+"</th>";
//	}
    table += "</tr></thead><tbody>";
    for (var i = 0; i < data.length; i++) {
        table += "<tr>";
        var res = data[i];
        for (var j = 0; j < attrs.length; j++) {
            var value = typeof(res[attrs[j]]) == "undefined" ? "" : res[attrs[j]];
            table += "<td>" + value + "</td>";
        }
        table += "</tr>";
    }
    table += "</tbody></table>";
    return table + addFixTableHeadElement(tableid);
}

function createSpan4LabelInput(labelValue, inputValue) {
    return "<div class='span4'><label class='control-label' >" + labelValue + "</label>" +
        "<input class='form-control' value='" + inputValue + "' type='text' style='margin-left:5px;' readonly='readonly'/></div>";
}

function createSpan4LabelText(labelValue, inputValue) {
    return "<div class='span4'><label class='control-label' >" + labelValue + "</label>" +
        "<textarea class='form-control' style='margin-left:5px;'/>" + inputValue + "</div>";
}

function createTableFive(data, title, column) {
    if (title.length != column.length) {
        return "";
    } else {
        var tableid = "fixheadtable" + new Date().getTime();
        var table = "<table id='" + tableid + "'" +
            " style='table-layout:fixed;word-wrap:break-word;' " +
            "class='table table-bordered  table-striped table-hover'" +
            " ><thead><tr>";
        for (var i = 0; i < title.length; i++) {
            table = table + "<th colspan='5'>" + title[i] + "</th>";
        }
        table += "</tr></thead><tbody>";
        for (var i = 0; i < data.length;) {
            table += "<tr>";
            var end = (i + 5) > data.length ? data.length : (i + 5);
            for (var j = i; j < end; j++) {
                var res = data[j];
                var value = typeof(res[column[0]]) == "undefined" ? "" : res[column[0]];
                table += "<td>" + value + "</td>";
            }
            i = end;
            table += "</tr>";
        }

        if (data.length == 0) {
            table += "<tr> <td colspan=" + title.length + ">Empty !</td></tr>";
        }

        table += "</tbody></table>";
        return table + addFixTableHeadElement(tableid);
    }
}

function createTableWithData(data, title) {
    if (title.length != 1) {
        return "";
    } else {
        var tableid = "fixheadtable" + new Date().getTime();
        var table = "<table id='" + tableid + "'" +
            " style='table-layout:fixed;word-wrap:break-word;' " +
            "class='table table-bordered  table-striped table-hover'" +
            " ><thead><tr>";
        for (var i = 0; i < title.length; i++) {
            table = table + "<th>" + title[i] + "</th>";
        }
        table += "</tr></thead><tbody>";
        for (var i = 0; i < data.length; i++) {
            table += "<tr><td>" + data[i] + "</td></tr>";
        }

        if (data.length == 0) {
            table += "<tr> <td colspan=" + title.length + ">Empty !</td></tr>";
        }

        table += "</tbody></table>";
        return table + addFixTableHeadElement(tableid);
    }

}

function createTable(data, title, column) {
    if (title.length != column.length) {
        return "";
    } else {
        var tableid = "fixheadtable" + new Date().getTime();
        var table = "<table id='" + tableid + "'" +
            " style='table-layout:fixed;word-wrap:break-word;' " +
            "class='table table-bordered  table-striped table-hover'" +
            " ><thead><tr>";
        for (var i = 0; i < title.length; i++) {
            table = table + "<th>" + title[i] + "</th>";
        }
        table += "</tr></thead><tbody>";
        for (var i = 0; i < data.length; i++) {
            table += "<tr>";
            var res = data[i];
            for (var j = 0; j < column.length; j++) {
                var value = typeof(res[column[j]]) == "undefined" ? "" : res[column[j]];
                table += "<td>" + value + "</td>";
            }
            table += "</tr>";
        }

        if (data.length == 0) {
            table += "<tr> <td colspan=" + title.length + ">Empty !</td></tr>";
        }

        table += "</tbody></table>";
        return table + addFixTableHeadElement(tableid);
    }

}

//页面组件、数据、x轴、y轴、标签
function createLineChart(ctx, data, x, y, label) {
    $("#" + ctx).html("");
    Morris.Area({
        element: ctx,
        data: data,
        xkey: x,
        ykeys: [y],
        labels: [label],
        fillOpacity: 0.5,
        parseTime: false,
        lineColors: ["#0B62A4"],
        lineWidth: 2
    });
}
// 不解析时间
function createBarChartNotParseTime(ctx, data, x, y, label) {
    $("#" + ctx).html("");
    Morris.Bar({
        parseTime: false,
        element: ctx,
        data: data,
        xkey: x,
        ykeys: [y],
        labels: [label],
        xLabelAngle: 45,
        barColors: function (row, series, type) {
            return "#FFA500";
        },
    });
    var barWidth = ($('#bar-example').width() / data.length) * (0.4);
    jQuery('rect').each(function (i) {
        var pos = $(this).offset();
        var top = pos.top - 15;
        var left = pos.left;
        left += barWidth / 2 + 3;

        $div = jQuery('<div class="xycount" style="top:' + top + 'px;left:' + left + 'px;" />');
        $div.text(data[i][y]); //get the count
        jQuery('body').append($div); //stick it into the dom
    });
}
//页面组件、数据、x轴、y轴、标签
function createBarChart(ctx, data, x, y, label) {
    $("#" + ctx).html("");
    Morris.Bar({
        element: ctx,
        data: data,
        xkey: x,
        ykeys: [y],
        labels: [label],
        xLabelAngle: 45
    });
}

//页面组件、数据、x轴、y轴、标签
function createMuiltLineChart(ctx, cols, data, x, y) {
    $("#" + ctx).html("");
    Morris.Area({
        element: ctx,
        data: data,
        xkey: x,
        ykeys: cols,
        labels: cols,
        fillOpacity: 0.5,
        parseTime: false,
        lineWidth: 2
    });
}

//页面组件、数据、x轴、y轴、标签
function createMuiltLineChartMore(ctx, cols, data, x) {
    $("#" + ctx).html("");
    Morris.Area({
        element: ctx,
        data: data,
        xkey: x,
        ykeys: cols,
        labels: cols,
        fillOpacity: 10,
        parseTime: false,
        lineWidth: 2
    });
}


$(document).ajaxStart(function () {
    $("form").find("div.error").removeClass("error");
    $("form").find("span.s2_help_inline").remove();
    parent.loading();
});
$(document).ajaxComplete(function () {
    parent.loadingOut();
});
$(document).ajaxError(function (event, request, settings) {
    if (request.status == 601 || request.status == 602) {
        if (self != top) {
            window.open("../login", "_top");
        }
    } else if (request.status == 504) {
        bootstrapAlert("查询超时！");
    } else {
        bootstrapAlert("未知错误：" + request.status);
    }

});

