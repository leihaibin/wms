<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/plugins/echarts/echarts-all.js"></script>
    <script>
        $(function() {
            var myChart = echarts.init(document.getElementById("main"));
                    option = {
                        title : {
                            text: '销售总额',
                            subtext:'${groupType}',
                            x:'center'
                        },
                        tooltip : {
                            trigger: 'item',
                            formatter: "{a} <br/>{b} : {c} ({d}%)"
                        },
                        legend: {
                            orient : 'vertical',
                            x : 'left',
                            data:${groupByList}
                        },

                        toolbox: {
                            show : true,
                            feature : {
                                mark : {show: true},
                                dataView : {show: true, readOnly: false},
                                magicType : {show: true, type: ['pie', 'funnel'],
                                    option: {
                                        funnel: {
                                            x: '25%',
                                            width: '50%',
                                            funnelAlign: 'left',
                                            max: ${max}
                                        }
                                    }
                                },
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        calculable : true,
                        series : [
                            {
                                name:'销售总额',
                                type:'pie',
                                radius : '55%',
                                center: ['50%', '60%'],
                                data:${data}

                            }
                        ]
                    };
            // 为echarts对象加载数据
            myChart.setOption(option);

        }
        );
    </script>
</head>
<body>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="height:400px;width: 600px"></div>
</body>
</html>
