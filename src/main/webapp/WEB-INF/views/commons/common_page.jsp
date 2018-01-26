<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/javascript">
    $(function(){
        //翻页
        $(".btn_page").click(function(){
            var pageNo=$(this).data("page")|| $("input[name=currentPage]").val();//获取要跳转的页码
            $("input[name=currentPage]").val(pageNo);
            $("#searchForm").submit();
        });
       //pageSize的onchange事件
        $(":input[name=pageSize]").change(function(){
            $(":input[name=currentPage]").val(1);
            $("#searchForm").submit();//提交高级查询
        });0
        //选中选择的pageSize选项
        $(":input[name=pageSize] option[value='${pageResult.pageSize}']").prop("selected",true);

    });
</script>
<div class="ui_tb_h30">
    <div class="ui_flt" style="height: 30px; line-height: 30px;">
        共有
        <span class="ui_txt_bold04">${pageResult.totalCount}</span>
        条记录，当前第
        <span class="ui_txt_bold04">${pageResult.currentPage}/${pageResult.totalPage}</span>
        页
    </div>
    <div class="ui_frt">
        <input type="button" value="首页" class="ui_input_btn01 btn_page" data-page="1"/>
        <input type="button" value="上一页" class="ui_input_btn01 btn_page" data-page="${pageResult.prevPage}"/>
        <input type="button" value="下一页" class="ui_input_btn01 btn_page" data-page="${pageResult.nextPage}"/>
        <input type="button" value="尾页" class="ui_input_btn01 btn_page" data-page="${pageResult.totalPage}"/>

        <select  name="pageSize" class="ui_select002">

            <option value="10">10</option>
            <option value="20">20</option>
            <option value="50">50</option>
        </select>
        转到第<input type="number" name="currentPage" style="width: 30px" min="1" max="${pageResult.totalPage}"
                  value="${pageResult.currentPage}" class="ui_input_txt01" />页
        <input type="button" class="ui_input_btn01 btn_page" value="跳转"/>
    </div>
</div>