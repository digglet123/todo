import { Component } from '@angular/core';
import { TodoService, TodoTask } from './todo.service';
import { OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  incompleteTodoTasks$: Observable<TodoTask[]> = of([]);
  completedTodoTasks$: Observable<TodoTask[]> = of([]);
  newTodoTask: string = '';

  constructor(private todoService: TodoService) {}

  ngOnInit() {
    this.fetchTodoTasks();
  }

  fetchTodoTasks() {
    const todos$ = this.todoService.getTodos();
    this.completedTodoTasks$ = todos$.pipe(
      map(tasks => tasks.filter(task => task.completed))
    );
    this.incompleteTodoTasks$ = todos$.pipe(
      map(tasks => tasks.filter(task => !task.completed))
    );
  }
  

  addTodoTask(description: string) {
    this.todoService.addTodoTask(description).subscribe(() => {
      this.fetchTodoTasks();
    });
  }

  deleteTodoTask(id: string) {
    this.todoService.deleteTodoTask(id).subscribe(() => {
      this.fetchTodoTasks();
    });
  }
  
  markTodoTaskAsCompleted(id: string) {
    this.todoService.markTodoTaskAsCompleted(id).subscribe(() => {
      this.fetchTodoTasks();
    });
  }

  trackByDescriptionId(index: number, todoTask: TodoTask): string {
    return todoTask.id;
  }

}
