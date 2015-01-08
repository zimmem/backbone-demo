$(function() {

	var Todo = Backbone.Model.extend({
		urlRoot : '/todo',
		validate : function(attrs, options) {
			if (!attrs.content) {
				alert("content can not be empty");
				return "content can not be empty";
			}
		}
	});

	var TodoList = Backbone.Collection.extend({
		model : Todo,
		url : '/todo'
	});

	var TodoView = Backbone.View
			.extend({
				events : {
					'click .del-btn' : 'deleteTodo',
					'click .save-btn' : 'saveTodo',
				},
				tagName : 'tr',
				render : function() {
					var html = this._mergeTempate(this.model.toJSON());
					console.info(html);
					this.$el.html(html);
					return this;
				},
				deleteTodo : function() {
					var that = this;
					this.model.destroy({
						success : function() {
							that.remove();
						}
					});
				},
				saveTodo : function() {
					this.model.save({
						content : this.$('.content-input').val()
					});
				},
				_mergeTempate : _
						.template('<td><%- id%></td><td><input class="content-input" value="<%- content%>"/><i class="save-btn glyphicon glyphicon-ok"/></td><td><i class="del-btn glyphicon glyphicon-trash"/></td>')
			});

	var TodoListView = Backbone.View.extend({
		el : '#todo-list',
		initialize : function() {
			var todos = new TodoList();
			this.listenToOnce(todos, 'sync', this.render);
			todos.fetch();
		},
		render : function(list) {
			var that = this;
			list.each(function(o, i) {
				console.info(o);
				var todoView = new TodoView({
					model : o
				}).render();
				that.$el.prepend(todoView.el);
			});
			return this;
		}
	});

	var CreateDialog = Backbone.View.extend({
		el : '#todo-dialog',
		events : {
			'click #add-btn' : 'save'
		},
		save : function() {
			var that = this;
			var todo = new Todo();
			todo.once('sync', function() {
				that.$el.modal("hide");
				that.trigger("create", todo);
			});
			todo.save({
				content : this.$('#content-input').val()
			});
		}
	});

	var World = Backbone.View.extend({
		events : {
			"click #create-btn" : "popCreateForm"
		},
		initialize : function() {
			var dialog = new CreateDialog();
			var listView = new TodoListView();
			this.listenTo(dialog, "create", function(todo) {
				var todoView = new TodoView({
					model : todo
				}).render();
				listView.$el.prepend(todoView.el);
			})
		}
	});

	new World();

});