<?php  
    
    $sid = $_POST["sid"];  
    $pass = $_POST["pass"];  
    

    // db details
    $user = "root";  
    $password = "ILgta77!@#";  
    $host ="localhost";  
    $db_name ="game"; 
    
    //check login data
    $con = mysqli_connect($host,$user,$password,$db_name);  
    
    //$sql = "select * from students where name='".$sid."' and password='".$pass."';";  

    //creating a query
	$stmt = $con->prepare("SELECT * FROM students WHERE name='".$sid."' AND password='".$pass."';");
	
	//executing the query 
	$stmt->execute();
	
	//binding results to the query 
	$stmt->bind_result($sid, $pass);
	
    $result = array(); 
    
    while($stmt->fetch()){
		$temp = array();
		$temp['sid'] = $sid; 
		$temp['pass'] = $pass; 
		array_push($result, $temp);
	}
	
	//displaying the result in json format 
	echo json_encode($result); 
    mysqli_close($con);  
?> 
