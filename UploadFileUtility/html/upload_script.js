	$(document).ready(
	
    function(){
    
		$(':button').click(function(){
	   	    var formData = new FormData($('form')[0]);
	   	    $.ajax({
				type: "POST",
				url: "http://localhost:8080/Upload-1/upload",
				xhr: function() {  // Custom XMLHttpRequest
					var myXhr = $.ajaxSettings.xhr();
					myXhr.upload;
					return myXhr;
				 },
				data: formData,
				//Options to tell jQuery not to process data or worry about content-type.
				cache: false,
				contentType: false,
				processData: false
	   	    
			}).done(function(response) {
			  	$(".lineitemslist").css("visibility", "visible");				
			}); 
		  });
		
		$(':file').change(function(){
			$(".lineitemslist").css("visibility", "hidden");		
			var file = this.files[0];
			var name = file.name;
			var size = file.size;
			var type = file.type;									
		});
    });
    
    
	

    

    
  
