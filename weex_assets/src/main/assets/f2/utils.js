function getClientSize() {
	var isIframe = self != top
	var width = document.documentElement.clientWidth
	var height = document.documentElement.clientHeight
	if (!isIframe) {
		height -= 10
	}
	return {
		width: width,
		height: height
	}
}

function createChart(eid) {
	var size = getClientSize()
	var canvas = document.getElementById(eid)
	canvas.width = size.width
	canvas.height = size.height
	var chart = new F2.Chart({
        id: eid,
        pixelRatio: window.devicePixelRatio // 指定分辨率
    });
    window.addEventListener('resize', function () {
        var size = getClientSize()
        chart.changeSize(size.width, size.height)
    });
    window.addEventListener('message', function (event) {
        var data = event.data
        if ('setData' === data.type) {
            chart.source(data.data);
        } else if ('render' === data.type) {
            // Step 3：创建图形语法，绘制柱状图，由 genre 和 sold 两个属性决定图形位置，genre 映射至 x 轴，sold 映射至 y 轴
            drawColumnChart()
            // Step 4: 渲染图表
            chart.render();
        }
    })
    return chart;
}