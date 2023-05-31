<html>
  <head>
    <title>Практика 3ІПЗ-20</title>
  </head>
  <body bgcolor="#D7BDE2">
    <h1>Вітаю на практиці!</h1>
    <#list productList as product>
        <div>
            <p>
                <b>${product.title}</b> ${product.price}
            </p>
        </div>
    </#list>
  </body>
</html>