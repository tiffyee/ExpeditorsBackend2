<html>
<head>
    <script type="text/javascript" src="/LarkU/js/jquery-1.8.2.js"></script>

    <style type="text/css">
        .box {
            padding: 8px;
            border: 1px solid blue;
            margin-bottom: 8px;
            width: 300px;
            height: 100px;
        }

        .newbox {
            padding: 8px;
            border: 1px solid red;
            margin-bottom: 8px;
            width: 200px;
            height: 50px;
        }
    </style>

</head>
<body>
<h1>jQuery append() and appendTo example</h1>

<div class="box">I'm a big box</div>

<div id="studentInfoDiv" class="box">I'm a big box 2</div>

<p>
    <button id="append">append()</button>
    <button id="appendTo">appendTo()</button>
    <button id="reset">reset</button>
</p>

<script type="text/javascript">

    $("#append").click(function () {

        $('.box').append("<div class='newbox'>I'm new box by append</div>");

    });

    $("#appendTo").click(function () {

        //$("<div class='newbox'>I'm new box by appendTo</div>").appendTo('#studentInfoDiv');
        var newContent = "<div class='newbox'>I'm new box by appendTo</div>";
        $(newContent).appendTo('#studentInfoDiv');

    });

    $("#reset").click(function () {
        location.reload();
    });

</script>
</body>
</html>