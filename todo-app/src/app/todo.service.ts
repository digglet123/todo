import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TodoTask {
  id: string;
  description: string;
  completed: boolean;
}

export interface TodoRequest {
  description: string;
}

@Injectable({
  providedIn: 'root'
})
export class TodoService {

  constructor(private httpClient: HttpClient) { }

  private url = 'http://localhost:8080/todos';

  getTodos(): Observable<TodoTask[]> {
    return this.httpClient.get<TodoTask[]>(this.url);
  }

  addTodoTask(description: string): Observable<TodoTask> {
    return this.httpClient.post<TodoTask>(this.url, { "description": description } as TodoRequest);
  }

  deleteTodoTask(id: string): Observable<void> {
    return this.httpClient.delete<void>(`${this.url}/${id}`);
  }

  markTodoTaskAsCompleted(id: string): Observable<TodoTask> {
    return this.httpClient.put<TodoTask>(`${this.url}/${id}/complete`, {});
  }

}
