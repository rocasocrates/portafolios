 $(document).ready (function () {
 
 
 	$(".service-box p").css('display', 'none');

 	$("#lenguajes").on('mouseenter', function(){
        
 		$('#lenguajes .service-box > p').slideDown('slow');
 		$('#bd .service-box > p').slideUp('slow');
 		$('#servidores .service-box > p').slideUp('slow');
 		$('#tecno .service-box > p').slideUp('slow');

 	});
 	$("#bd").on('mouseenter', function(){
        
 		$('#lenguajes .service-box > p').slideUp('slow');
 		$('#bd .service-box > p').slideDown('slow');
 		$('#servidores .service-box > p').slideUp('slow');
 		$('#tecno .service-box > p').slideUp('slow');

 	});
 	$("#servidores").on('mouseenter', function(){
        
 		$('#lenguajes .service-box > p').slideUp('slow');
 		$('#bd .service-box > p').slideUp('slow');
 		$('#servidores .service-box > p').slideDown('slow');
 		$('#tecno .service-box > p').slideUp('slow');

 	});
 	$("#tecno").on('mouseenter', function(){
        
 		$('#lenguajes .service-box > p').slideUp('slow');
 		$('#bd .service-box > p').slideUp('slow');
 		$('#servidores .service-box > p').slideUp('slow');
 		$('#tecno .service-box > p').slideDown('slow');

 	});
  	$('#lenguajes', '#bd', '#servidores', '#tecno').on('mouseleave', function(){

  		$('#lenguajes', '#bd', '#servidores', '#tecno .service-box > p').slideUp('slow');
  		
  	});
 	

 

     })

 // $(document).ready (function () {
 // 	var efec = false;
 // 	$(".service-box p").css('display', 'none');
 // 	$("#lenguaje").css('border', 'solid');

 // 	$("#lenguaje").on('mouseenter', function(){

 // 		$('#lenguaje .service-box > p').slideDown('slow');

 // 	});
 // 	$('#lenguaje').on('mouseleave', function(){

 // 		$('#lenguaje .service-box > p').slideUp('slow');
 // 	});



 //     })