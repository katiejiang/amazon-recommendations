Router.route('/', function () {
    Session.set('view', 'search');
    this.render('main');
});

Router.route('/:id', function () {
    var productId = this.params.id;
    var product = Products.findOne({'id': productId});
    if (product) {
        Session.set('product', product);
        var productList = [];
        if (product.recs) {
            for (var id of product.recs) {
                productList.push(Products.findOne({'id': id}))
            }
        }
        Session.set('products', productList);
        Session.set('view', 'product');
    } else {
        Session.set('products', []);
        Session.set('view', 'search');
    }
    this.render('main');
})
