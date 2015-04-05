x = 1
for(var i = 0; i<100; i++){
  setTimeout(function(){$.ajax({
      contentType: 'application/json',
      data: JSON.stringify({
          "hue":x++,
          "saturation":100,
          "level":50
      }),
      dataType: 'json',
      success: function(data){},
      error: function(){},
      processData: false,
      type: 'PUT',
      url: 'https://graph.api.smartthings.com/api/smartapps/installations/d7a29a5f-e184-46f7-89ec-fa559b95465e/color',
      headers: {"Authorization":"Bearer 2515bdb1-5cf3-44e7-b757-0402e0af43cc"}
  });}, i*100);
}