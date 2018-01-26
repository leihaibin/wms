
    $(function () {
        $("#btn_login").click(function () {
            $("#submitForm").ajaxSubmit(function (data) {
                if (data.success) {
                    window.location.href = "/main.do";
                } else {
                    $("#login_err").html(data.msg);
                }
            });
        });
    });
