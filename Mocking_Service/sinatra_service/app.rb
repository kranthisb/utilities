# To run this app...First install Sinatra .. a) sudo gem install sinatra b) sudo gem install json
# Run this app : sudo ruby app.rb

require 'sinatra'
require 'json'

#curl -i -X POST -d '{"name":"feed1","desc":"Test Feed","attributes":[{"name":"Attr1"},{"name":"Attr2"}]}' http://localhost:4567/feed/create
post '/feed/create' do
  content_type :json
  params = JSON.parse(request.body.read)
  { 
    :id => "1234", 
  	:name 			=> params['name'],
  	:desc 			=> params['desc'], 
  	:create_date 	=> '01-Jan-2015',
 	:attributes 	=>
 					[
 						{
 							:attr_id => "111",
							:name 	 => params['attributes'][0]['name'] 
						},
						{
							:attr_id => "222",
							:name 	 => params['attributes'][1]['name'] 
						}
					]
  }.to_json
  #params.to_json
end
