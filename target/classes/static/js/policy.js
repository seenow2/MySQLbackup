
/**
 * @description:  当备份模式改变时
 * @param:
 * @return: null
 * @author: seenow
 * @date: 2021-06-29 21:50:29
 */
function bakModChange() {
    var isCplBak = $("#completeBackup").val();
    var dbId = $("#dbId").val();
    var tableName = $("#hTableName").val();//默认值
    if (isCplBak == 1){  //全备
        $("#tableName").empty();
        $("#signfld").empty();
        $("#daysLen").val("");
        $("#delSign").val("0");
    }else{
        //动态添加列表框中的表名 tpolicyId
        if ($("#daysLen").val()=="" || $("#daysLen").val()=="0"){$("#daysLen").val("120");}
        var url = '/getTables?dbid=' + dbId + "&tblName=" + tableName;

        $("#tableName").load(url, () => { //必须要等到load完成之后再执行回调函数
            tblNameChang();
        });

    }

}

function oldbakModChange() {
    var isCplBak = $("#completeBackup").val();
    var dbId = $("#dbId").val();
    if (isCplBak == 1){  //全备
        $("#tableName").empty();
        $("#signfld").empty();
        $("#daysLen").val("");
        $("#delSign").val("0");
        $("#tableName").attr("disabled", true);
        $("#signfld").attr("disabled", true);
        $("#daysLen").attr("disabled", true);
        $("#delSign").attr("disabled", true);
    }else{
        $("#tableName").attr("disabled", false);
        $("#signfld").attr("disabled", false);
        $("#daysLen").attr("disabled", false);
        $("#delSign").attr("disabled", false);
        //动态添加列表框中的表名
        //var url = '/getTables?dbid='+ dbId;
        var url = '/getTables';


        $.ajax({
            type: "POST",
            url: url,
            async: false,
            //contentType: 'application/text;charset=UTF-8',
            //dataType: 'text',
            //data: JSON.stringify(param),
            data: {dbid:dbId},

            success: function(data){
                console.log("succeed");

                var modelList = data.modelList;
                console.log("DATA:"+modelList.length);
                for (var i in data) {        //遍历输出LIST添加为option
                    alert("I="+i);
                    $("#tableName").append("<option value='" + data[i] + "'>" + data[i] + "</option>");
                }
            },
            error: function(){
                console.log("failed.");
            }
        });

        //$('#tableName').load(url);
        //var hTableName = $("#hTableName").val();
        //alert($("#tableName").find("option").length);



        //$("#tableName option[value="+hTableName+"]").prop("selected",true)

    }

}

/**
 * @description:  当表名改变时，其字段名也随之发生改变
 * @param:
 * @return: null
 * @author: seenow
 * @date: 2021-06-29 17:29:54
 */
function tblNameChang() {

    var talName = $("#tableName").val();
    var dbId = $("#dbId").val();
    var signfld = $("#hSigFld").val();
    var url = '/getFields?dbid='+ dbId + "&tblName=" + talName + "&signfld=" + signfld;
    //alert(url);
    $('#signfld').load(url);
};

/**
 * @description:  提交前先验证一下
 * @param:
 * @return: null
 * @author: seenow
 * @date: 2021-07-13 23:08:03
 */
function check() {
    //todo:验证
    $("#daysLen").val($("#daysLen").val().replace(/\D/g,''));
}


/*    $(document).ready(function(){
        $("#tableName").on("bakModChange",function(){
            var hTableName = $("#hTableName").val();
            alert($("#tableName").find("option").length);
            $("#tableName option[value="+hTableName+"]").prop("selected",true)
        });
    });*/
