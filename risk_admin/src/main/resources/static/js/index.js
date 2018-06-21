$(document).ready(function(){
	
	renderChart1();
	renderChart2();
	renderChart3();
	
});

function renderChart3(){
	var ctxChart = $("#myChart3").get(0).getContext("2d");
	new Chart(ctxChart,{
		type:"pie",
		data:getData3(),
		options:{
			title:{
				display: true,
				text:"支付方式统计"
			},
			layout: {
	            padding: {
	                bottom: 10
	            }
	        },
		}
	});
}
function getData3(){
	var data = {
			labels: ["扫码支付", "H5支付", "快捷支付", "网关支付"],
			datasets: [{
	            backgroundColor: 'green',
	            data: [121, 191, 13, 335],
	            backgroundColor: [
	          			        'rgba(0,255,0,0.5)',    // color for data at index 0
	        			        'rgba(255,0,0,0.5)',   // color for data at index 1
	        			        'rgba(0,0,255,0.5)',  // color for data at index 2
	        			        'rgba(100,100,100,0.5)',  // color for data at index 3
	        			        //...
	        	],
	            borderWidth: 1
	        }]
	};
	return data;
}

function renderChart2(){
	var ctxChart = $("#myChart2").get(0).getContext("2d");
	new Chart(ctxChart,{
		type:"radar",
		data:getData2(),
		options:{
			title:{
				display: true,
				text:"支付类别统计"
			},
			layout: {
	            padding: {
	                bottom: 10
	            }
	        }
		}
	});
}

function getData2(){
	var data = {
			labels: ["微信支付", "支付宝支付", "QQ支付", "银联支付", "京东支付", "其他支付"],
			datasets: [{
	            label: '成功交易',
	            backgroundColor: 'rgba(0,255,0,0.5)',
	            data: [12, 19, 3, 5, 2, 3],
	            borderWidth: 1
	        },
	        {
	            label: '失败交易',
	            data: [3, 12, 1, 3, 2, 1],
	            backgroundColor: 'rgba(255,0,0,0.5)',
	            borderWidth: 1
	        }]
	};
	return data;
}

function renderChart1(){
	var ctxChart1 = $("#myChart1").get(0).getContext("2d");
	new Chart(ctxChart1,{
		type:"line",
		data:getData1(),
		options:getOptions1()
	});
}

function getData1(){
	var data = {
			labels: ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"],
	        datasets: [{
	            label: '交易成功',
	            data: [12, 19, 3, 5, 2, 3,112, 119, 113, 115, 112, 143,412, 119, 13, 51, 12, 31,12, 19, 3, 5, 2, 3],
	            backgroundColor : "rgba(0,255,0,0.5)",
//	            borderColor: 'gray',
	            borderWidth: 1
	        },
	        {
	            label: '交易失败',
	            data: [2, 9, 3, 5, 2, 3,12, 19, 13, 15, 12, 13,42, 19, 1, 5, 1, 3,2, 9, 3, 5, 2, 3],
	            backgroundColor : "rgba(255,0,0,0.5)",
//	            borderColor: 'gray',
	            borderWidth: 1
	        }]
		}
	return data;
}

function getOptions1(){
	var options = {
//        scales: {
//            yAxes: [{
//                ticks: {
//                    beginAtZero:true
//                }
//            }]
//        },
//        events: ['click']
//        tooltips: {
//            mode: 'nearest'
//        }
//        title: {
//            display: true,
//            text: 'Custom Chart Title'
//        }
    }
	return options;
}

