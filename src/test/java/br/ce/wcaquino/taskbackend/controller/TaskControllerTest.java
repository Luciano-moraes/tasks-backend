package br.ce.wcaquino.taskbackend.controller;


import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;


public class TaskControllerTest {
	
	@Mock
	private TaskRepo taskRepo;
	
	@InjectMocks
	private TaskController controller;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void naoDeveSalvarTarefaSemDescriacao() {
		Task todo = new Task();
		todo.setDueDate(LocalDate.now());
		controller = new TaskController();
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar nesse ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}

    }

    @Test
    public void naoDeveSalvarTarefaSemData() {
    	Task todo = new Task();
//		todo.setDueDate(LocalDate.now());
		todo.setTask("Descrição");
		controller = new TaskController();
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar nesse ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
    	

    }
    
    @Test
    public void naoDeveSalvarTarefaComDataPassada() {
    	Task todo = new Task();
		todo.setDueDate(LocalDate.of(2010,01,01));
		todo.setTask("Descrição");
		controller = new TaskController();
		try {
			controller.save(todo);
			Assert.fail("Não deveria chegar nesse ponto!");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}
    
    }
    
    @Test
    public void DeveSalvarTarefaComSucesso() throws ValidationException {
    	Task todo = new Task();
		todo.setDueDate(LocalDate.now());
		todo.setTask("Descrição");
		controller.save(todo);
		Mockito.verify(taskRepo).save(todo);
	
    }

}