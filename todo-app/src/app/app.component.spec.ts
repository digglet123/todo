import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { provideHttpClientTesting } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { TodoService, TodoTask } from './todo.service';
import { FormsModule } from '@angular/forms';
import { By } from '@angular/platform-browser';

describe('AppComponent', () => {
  let component: AppComponent;
  let fixture: ComponentFixture<AppComponent>;
  let todoService: jasmine.SpyObj<TodoService>;

  const mockTodoTasks: TodoTask[] = [
    { id: '1', description: 'Task 1', completed: false },
    { id: '2', description: 'Task 2', completed: true }
  ];

  beforeEach(async () => {
    const todoServiceSpy = jasmine.createSpyObj('TodoService', ['getTodos', 'addTodoTask', 'deleteTodoTask', 'markTodoTaskAsCompleted']);

    await TestBed.configureTestingModule({
      declarations: [AppComponent],
      imports: [FormsModule, RouterTestingModule, HttpClientTestingModule],
      providers: [
        provideHttpClientTesting(),
        { provide: TodoService, useValue: todoServiceSpy }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    todoService = TestBed.inject(TodoService) as jasmine.SpyObj<TodoService>;

    todoService.getTodos.and.returnValue(of(mockTodoTasks));
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });


  it('should fetch todo tasks on init', () => {
    component.ngOnInit();
    expect(todoService.getTodos).toHaveBeenCalled();
    component.incompleteTodoTasks$.subscribe(tasks => {
      expect(tasks.length).toBe(1);
      expect(tasks[0].description).toBe('Task 1');
    });
    component.completedTodoTasks$.subscribe(tasks => {
      expect(tasks.length).toBe(1);
      expect(tasks[0].description).toBe('Task 2');
    });
  });

  it('should add a new todo task', () => {
    const newTaskDescription = 'New Task';
    todoService.addTodoTask.and.returnValue(of({ id: '1', description: 'Task 1', completed: false } as TodoTask));
    component.addTodoTask(newTaskDescription);
    expect(todoService.addTodoTask).toHaveBeenCalledWith(newTaskDescription);
    expect(todoService.getTodos).toHaveBeenCalledTimes(1);
  });

  it('should delete a todo task', () => {
    const taskId = '1';
    todoService.deleteTodoTask.and.returnValue(of(void 0));
    component.deleteTodoTask(taskId);
    expect(todoService.deleteTodoTask).toHaveBeenCalledWith(taskId);
    expect(todoService.getTodos).toHaveBeenCalledTimes(1);
  });

  it('should mark a todo task as completed', () => {
    const taskId = '1';
    todoService.markTodoTaskAsCompleted.and.returnValue(of({ id: '1', description: 'Task 1', completed: true } as TodoTask));
    component.markTodoTaskAsCompleted(taskId);
    expect(todoService.markTodoTaskAsCompleted).toHaveBeenCalledWith(taskId);
    expect(todoService.getTodos).toHaveBeenCalledTimes(1);
  });

  it('should call addTodoTask when Enter key is pressed', () => {
    const newTaskDescription = 'New Task';
    component.newTodoTask = newTaskDescription;
    spyOn(component, 'addTodoTask');
    const input = fixture.debugElement.query(By.css('input')).nativeElement;
    input.dispatchEvent(new KeyboardEvent('keyup', { key: 'Enter' }));
    fixture.detectChanges();
    expect(component.addTodoTask).toHaveBeenCalledWith(newTaskDescription);
  });

});
