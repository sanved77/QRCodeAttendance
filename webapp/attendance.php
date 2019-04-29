<?php  
    
    $day = $_POST["day"]; 
    $month = $_POST["month"]; 
    $year = $_POST["year"]; 

    $list = "";
    $pre = "";

    // db details
    $user = "root";  
    $password = "ILgta77!@#";  
    $host ="localhost";  
    $db_name ="game"; 
    
    //check login data
    $con = mysqli_connect($host,$user,$password,$db_name);  

    //creating a query
	
	//$stmt = $con->prepare("SELECT * FROM register WHERE day='".(string)$day."' AND month='".(string)$month."' AND year='".(string)$year."';");
	$stmt = $con->prepare("SELECT * FROM register WHERE day='".$day."' AND month='".$month."' AND year='".$year."';");
	//$stmt = $con->prepare("SELECT * FROM register WHERE day='4' AND month='28' AND year='2019';");
	
	//executing the query 
    $stmt->execute();
    
    //binding results to the query 
	$stmt->bind_result($sid, $day, $month, $year);
	
    $result = array(); 
    
    while($stmt->fetch()){
		$temp = array();
		$temp['sid'] = $sid; 
		$temp['day'] = $day; 
		$temp['month'] = $month; 
        $temp['year'] = $year; 
		$list = $list . " - " . $sid . "<br/>";
        array_push($result, $temp);
        $pre = "Students present on " . $month . "/" . $day . "/" . $year;
	}
	

    echo "

        <!DOCTYPE html>
        <html>
        <head>
            <title>Today's Attendence</title>
        </head>
        <style>
            
            .ptext{
                font-size: 20px;
                text-align: center;
            }

            #tab{
            	font-family: Georgia, serif;
                font-size: 25px;
                letter-spacing: 2px;
                word-spacing: 2px;
                color: #000000;
                font-weight: normal;
                text-decoration: none;
                font-style: normal;
                font-variant: normal;
                text-transform: none;
            }

        </style>
        <body>
        <h1 style='text-align: center;'>Attendence List</h1>
        <br>
        <div class='ptext'>
            <form action='test.php' method='post'>
                Month<input type='number' style='width:50px' name='month' min='1' max='12'>
                Day<input type='number' style='width:50px' name='day' min='1' max='31'>
                Year<input type='number' style='width:50px' name='year' min='2012' max='2019'>
                <br>
                <br>
                <input value='Check Roll List' type='submit'>
            </form>

            <p id='tab'> " . $pre . " </p>
            <p id='tab'> " . $list . " </p>
            
        </div>
        </body>
        </html>

    "
?> 