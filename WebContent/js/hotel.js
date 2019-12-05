$(function() {
	
	//温泉
	$(".tu_s").hide();
	$(".gen").click(function() {
		$(".tu_s").show(1000);
		$(".gen").hide(1000);
	});
	
	
	//生态
	$(".genBB").hide();
	$("#genB").click(function() {
		$(".genBB").show(800);
		$("#genB").hide(800);
	});
	
	
	
	//港澳
	$(".genAA").hide();
	$("#genA").click(function() {
		$(".genAA").show(300);
		$("#genA").hide(300);
	});
	
	/**
	 * 控制温泉的显示
	 */
	$("#y1").click(function (){
		$("#a1").show(1000);
		$("#a2").hide(1000);
		$("#a3").hide(1000);
		$("#a4").hide(1000);
		$("#y1").css({"background-color":"#ff4500","color":"white"});
		$("#y2").css({"background-color":"white","color":"black"});
		$("#y3").css({"background-color":"white","color":"black"});
		$("#y4").css({"background-color":"white","color":"black"});
	});
   $("#y2").click(function (){
		$("#a1").hide(1000);
		$("#a2").show(1000);
		$("#a3").hide(1000);
		$("#a4").hide(1000);
		$("#y1").css({"background-color":"white","color":"black"});
		$("#y2").css({"background-color":"#ff4500","color":"white"});
		$("#y3").css({"background-color":"white","color":"black"});
		$("#y4").css({"background-color":"white","color":"black"});
		
	});
   $("#y3").click(function (){
		$("#a1").hide(1000);
		$("#a2").hide(1000);
		$("#a3").show(1000);
		$("#a4").hide(1000);
		$("#y1").css({"background-color":"white","color":"black"});
		$("#y2").css({"background-color":"white","color":"black"});
		$("#y3").css({"background-color":"#ff4500","color":"white"});
		$("#y4").css({"background-color":"white","color":"black"});
    });
    $("#y4").click(function (){
    	$("#a1").hide(1000);
		$("#a2").hide(1000);
		$("#a3").hide(1000);
		$("#a4").show(1000);
		$("#y1").css({"background-color":"white","color":"black"});
		$("#y2").css({"background-color":"white","color":"black"});
		$("#y3").css({"background-color":"white","color":"black"});
		$("#y4").css({"background-color":"#ff4500","color":"white"});
});
    
    /**
     * 控制长隆显示
     */
    $("#z1").click(function (){
    	$("#zz1").show(1000);
		$("#zz2").hide(1000);
		$("#z1").css({"background-color":"#ff4500","color":"white"});
		$("#z2").css({"background-color":"white","color":"black"});
    });
   $("#z2").click(function (){
	    $("#zz1").hide(1000);
		$("#zz2").show(1000);
		$("#z1").css({"background-color":"white","color":"black"});
		$("#z2").css({"background-color":"#ff4500","color":"white"});
    });
    
   
   /**
    * 控制度假酒店显示
    */
      $("#p1").click(function (){
   	    $("#pp1").show(1000);
		$("#pp2").hide(1000);
		$("#p1").css({"background-color":"#ff4500","color":"white"});
		$("#p2").css({"background-color":"white","color":"black"});
   });
     $("#p2").click(function (){
	    $("#pp1").hide(1000);
		$("#pp2").show(1000);
		$("#p1").css({"background-color":"white","color":"black"});
		$("#p2").css({"background-color":"#ff4500","color":"white"});
   });
   
     /**
      * 控制海滩酒店
      */
 	$("#u1").click(function (){
		$("#uu1").show(1500);
		$("#uu2").hide(1500);
		$("#uu3").hide(1500);
		$("#u1").css({"background-color":"#ff4500","color":"white"});
		$("#u2").css({"background-color":"white","color":"black"});
		$("#u3").css({"background-color":"white","color":"black"});
	});
   $("#u2").click(function (){
		$("#uu1").hide(1000);
		$("#uu2").show(1000);
		$("#uu3").hide(1000);
		$("#u1").css({"background-color":"white","color":"black"});
		$("#u2").css({"background-color":"#ff4500","color":"white"});
		$("#u3").css({"background-color":"white","color":"black"});
		
	});
   $("#u3").click(function (){
		$("#uu1").hide(1500);
		$("#uu2").hide(1500);
		$("#uu3").show(1500);
		$("#u1").css({"background-color":"white","color":"black"});
		$("#u2").css({"background-color":"white","color":"black"});
		$("#u3").css({"background-color":"#ff4500","color":"white"});
    });
  
   /**
    * 控制港澳酒店
    */
   $("#t1").click(function (){
  	    $("#tt1").show(1000);
		$("#tt2").hide(1000);
		$("#t1").css({"background-color":"#ff4500","color":"white"});
		$("#t2").css({"background-color":"white","color":"black"});
  });
    $("#t2").click(function (){
	    $("#tt1").hide(1000);
		$("#tt2").show(1000);
		$("#t1").css({"background-color":"white","color":"black"});
		$("#t2").css({"background-color":"#ff4500","color":"white"});
  });
});