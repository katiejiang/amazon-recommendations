import { Template } from 'meteor/templating';
import { ReactiveVar } from 'meteor/reactive-var';

import './main.html';

Template.main.helpers({
    view : function () {
        return Session.get('view');
    }
})

Template.header.events({
    'submit #search-product-form': function (event) {
        event.preventDefault();
        var query = event.target.searchQuery.value;
        console.log(query);
        if (query) {
            query = new RegExp(query, 'i');
            // show the first 50 results
            var products = Products.find({'title': {$regex: query}}).fetch().slice(0,50);
            console.log(products);
            Session.set('products', products);
        } else {
            Session.set('products', []);
        }
        Router.go('/');
    }
})

Template.search.helpers({
    products : function () {
        return Session.get('products');
    }
})

Template.product.helpers({
    product : function () {
        return Session.get('product');
    }
})

Template.productList.helpers({
    products : function () {
        return Session.get('products');
    }
})

Template.productList.events({
    'click .recommendations-btn' : function (event) {
        var id = event.target.id;
        if (id) {
            Router.go('/' + id);
        }
    }
})
