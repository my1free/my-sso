/**
 * Created by michealyang on 17/3/6.
 */

/**
 * 统一Ajax返回数据封装
 */
function RespData(success, msg, data){
    this.success = success;
    this.data = data;
    this.msg = msg;
}

var DlgProxy = {
    swalSimpleSuccess: function(msg){
        swal({
            title: msg,
            type: "success"
        });
        KeyboardUtil.unbindEnter();
    },
    swalSimpleWarning: function(msg){
        swal({
            title: msg,
            type: "warning"
        });
        KeyboardUtil.unbindEnter();
    },
    swalSimpleError: function(msg){
        swal({
            title: msg,
            type: "error"
        });
        KeyboardUtil.unbindEnter();
    },
    info: function(msg){
        $("#noticeDiv").remove();
        var dlg = '<div id="noticeDiv" class="col-md-3" style="position: fixed; top: 50px; right:10px; z-index: 9999;" hidden class="col-md-3 col-xs-12 alert TYPE ft-size-16 role="alert"><span><strong><i class="fa fa-info"></i>  INFO!  </strong></span>MSG</div>';
        var infoDlg = dlg.replace("MSG", msg).replace("TYPE", "alert-info");
        $('html').append(infoDlg);
        $("#noticeDiv").fadeIn();
        window.setTimeout(function(){
            $("#noticeDiv").fadeOut();
        }, 1500);
    },
    success: function(msg){
        $("#noticeDiv").remove();
        var dlg = '<div id="noticeDiv" style="position: fixed; top: 50px; right:10px; z-index: 9999;" hidden class="col-md-3 col-xs-12 alert TYPE ft-size-16" role="alert"><span><strong><i class="fa fa-success"></i>  SUCCESS!  </strong></span>MSG</div>';
        var infoDlg = dlg.replace("MSG", msg).replace('TYPE', 'alert-success');
        $('html').append(infoDlg);
        $("#noticeDiv").fadeIn();
        window.setTimeout(function(){
            $("#noticeDiv").fadeOut();
        }, 1500)
    },
    error: function(msg){
        $("#noticeDiv").remove();
        var dlg = '<div id="noticeDiv" style="position: fixed; top: 50px; right:0px; z-index: 9999;" hidden class="col-md-3 col-xs-12 alert TYPE ft-size-16" role="alert"><span><strong><i class="fa fa-warning"></i>  ERROR!  </strong></span>MSG</div>';
        var infoDlg = dlg.replace("MSG", msg).replace('TYPE', 'alert-danger');
        $('html').append(infoDlg);
        $("#noticeDiv").fadeIn();
        window.setTimeout(function(){
            $("#noticeDiv").fadeOut();
        }, 1500)
    }
}

var FormValueUtil = {
    getInputValue : function(id, data){
        if(data == undefined || data == null) data = {}
        $("#" + id).find("input").each(function (i, e) {
            var name = e.getAttribute("name");
            var value = $.trim(e.value);
            data[name] = value;
        });
        return data;
    }
}

var AMapProxy = {
    /**
     * 添加普通的marker。
     * <p>有了AwesomeMarker，这个估计很少用了</p>
     * @param markers
     * @param map
     * @returns {string}
     */
    addSimpleMarker: function(markers, map){
        if(JSBasic._typeof(markers) != "array"){
            return "格式错误";
        }
        markers.forEach(function(ele, i){
            var marker = new AMap.Marker({
               position: ele.position.split(","),
               title: ele.title
            })
            marker.setMap(map);
            marker.setLabel({//label默认蓝框白底左上角显示，样式className为：amap-marker-label
                offset: new AMap.Pixel(20, 20),//修改label相对于maker的位置
                content: ele.alias
            });
        });
    },

    /**
     * 添加awesome Icon
     * @param markers
     * @param map
     * @param awesomeIcon: font-awesome css style
     * @param iconStyle: red, orange, green ,blue, orchid, darkred, darkblue, darkgreen, purple, cadetblue, salmon, beige, lightgreen, lightblue, pink, lightpink, white, lightgray, gray, black
     *      @see http://lbs.amap.com/api/javascript-api/reference-amap-ui/overlay/simplemarker#iconStyle
     * @param fontColor
     *
     * @see http://lbs.amap.com/api/javascript-api/reference-amap-ui/overlay/awesomemarker
     */
    addAwesomeMarker : function(markers, map, awesomeIcon, iconStyle, fontColor) {
        if(awesomeIcon == null){
            awesomeIcon = "arrows";
        }
        if(iconStyle == null){
            iconStyle = "orange";
        }
        if(fontColor == null){
            fontColor = "#333";
        }

        AMapUI.loadUI(['overlay/AwesomeMarker'], function (AwesomeMarker) {
            markers.forEach(function (ele, i) {
                var marker = new AwesomeMarker({

                    //设置awesomeIcon
                    awesomeIcon: awesomeIcon, //可用的icons参见： http://fontawesome.io/icons/

                    //下列参数继承自父类

                    //iconLabel中不能包含innerHTML属性（内部会利用awesomeIcon自动构建）
                    iconLabel: {
                        style: {
                            color: fontColor //设置颜色
                        }
                    },
                    iconStyle: iconStyle, //设置图标样式

                    //基础的Marker参数
                    map: map,
                    position: ele.position.split(",")
                });
            });
        });
    },

    clearMap: function(map){
        //
    }
}

var AjaxProxy = {
    queryByPost: function(url, data){
        var result = new RespData(false, "no response", null);
        $.ajax({
            url: url,
            type: "POST",
            async: false,
            data: data,
            dataType: "json",
            success: function(response){
                if(response.code == 1){
                    result = new RespData(true, response.msg, response.data);
                }else{
                    result = new RespData(false, response.msg, null);
                }
            },
            error: function(e){
                result = new RespData(false, "请求错误", null);
            }
        });
        return result;
    },

    queryByGet : function(url){
        var result = new RespData(false, "no response", null);
        $.ajax({
            url: url,
            type: "GET",
            async: false,
            dataType: "json",
            success: function(response){
                if(response.code == 1){
                    result = new RespData(true, response.msg, response.data);
                }else{
                    result = new RespData(false, response.msg, null);
                }
            },
            error: function(e){
                result = RespData(false, "请求错误", null);
            }
        });
        return result;
    }
}

/**
 * JS基础函数补充
 */
var JSBasic = {

    /**
     * 判断数据类型
     * @param obj
     * @returns {*}
     * @private
     */
    //当然为了兼容IE低版本，forEach需要一个polyfill，不作细谈了。
    _typeof: function(obj) {
        var class2type = {};
        "Boolean Number String Function Array Date RegExp Object Error".split(" ").forEach(function (e, i) {
            class2type["[object " + e + "]"] = e.toLowerCase();
        });
        if (obj == null) {
            return String(obj);
        }
        return typeof obj === "object" || typeof obj === "function" ?
        class2type[class2type.toString.call(obj)] || "object" :
            typeof obj;
    }
}

var KeyboardUtil = {
    //绑定回车响应
    bindEnter: function($dom){
        document.onkeyup=function keyListener(e){
            //  当按下回车键，执行我们的代码
            if(e.keyCode == 13){
                $dom.click();
            }
        }
    },
    //解绑回车响应
    unbindEnter: function(){
        document.onkeyup=null;
    }
}
