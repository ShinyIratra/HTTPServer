<?php
    echo "Bonjour";
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <img src="ad-image-2.png"> </img>
    <ul>
    <?php for($i = 0; $i < 10; $i++) { ?>
        <li> <?php echo $i; ?> </li>
    <?php } ?>
    </ul>

    <form action="test2.php" method="post">
        <input type="text" name="texte" placeholder="Texte">
        <input type="text" name="name" placeholder="Texte">
        <input type="submit" value="Entrer">
    </form>
</body>
</html>