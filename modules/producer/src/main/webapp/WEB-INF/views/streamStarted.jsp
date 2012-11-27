<!DOCTYPE html>

<html>
<head>
<title>Complex Event Processing POC - Producer</title>

  <meta charset="utf-8" >
  <link rel="stylesheet" href="/cep/styles/techarch.css">
  <link rel="stylesheet" href="/cep/styles/forms.css">

</head>

  <body>
  <div id="container">
    <header>
        <h1>Complex Event Processing POC</h1>
        <h2>Event Producer</h2>
    </header>

    <section>
      <article>
        <h3>Event Stream Initiated</h3>
        <p>
        <i>The event stream was initiated.</i>
        </p>
        <p>
<%
    if (request.getAttribute("isInfinite").toString().equalsIgnoreCase("true")) {
        out.println("Infinite event stream started.");
    }
    else {
        out.println(request.getAttribute("numMessages") + " event(s) sent.");
    }
%>
        </p>
        <p>Click <a href="/cep/index.html">here</a> to return to the previous page to start a new event stream.</p>
      </article>
    </section>

    <footer>
		<p align="center"><img src="/cep/images/TA-Logo.JPG" alt="Technology Architecture"></p>
        <h4><p align="center">Powered by HTML5</p></h4>
    </footer>

  </div>
 </body>

</html>
