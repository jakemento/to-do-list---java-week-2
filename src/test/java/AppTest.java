import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import java.time.LocalDateTime;



import static org.assertj.core.api.Assertions.assertThat;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
      return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();
//INTEGRATION TESTING
  @Test
  public void rootTest() {
      goTo("http://localhost:4567/");
      assertThat(pageSource()).contains("Task list!");
  }

  @Test
  public void taskIsCreatedTest() {
    goTo("http://localhost:4567/");
    fill("#description").with("Mow the lawn");
    submit(".btn");
    assertThat(pageSource()).contains("Your task has been saved.");
  }

  @Test
  public void taskIsDisplayedTest() {
    goTo("http://localhost:4567/");
    fill("#description").with("Mow the lawn");
    submit(".btn");
    click("a", withText("Go Back"));
    assertThat(pageSource()).contains("Mow the lawn");
  }

  @Test
  public void multipleTasksAreDisplayedTest() {
    goTo("http://localhost:4567/");
    fill("#description").with("Mow the lawn");
    submit(".btn");
    click("a", withText("Go Back"));
    fill("#description").with("Buy groceries");
    submit(".btn");
    click("a", withText("Go Back"));
    assertThat(pageSource()).contains("Mow the lawn");
    assertThat(pageSource()).contains("Buy groceries");
  }

// UNIT TESTING
  @Test
  public void Task_instantiatesCorrectly_true() {
    Task myTask = new Task("Mow the lawn");
    assertEquals(true, myTask instanceof Task);
  }

  @Test
  public void task_instantiatesWithDescription_true() {
    Task myTask = new Task("Mow the lawn");
    assertEquals("Mow the lawn", myTask.getDescription());
  }

  @Test
  public void isCompleted_isFalseAfterInstantiaon_false() {
    Task myTask = new Task("Mow the lawn");
    assertEquals(false, myTask.isCompleted());
  }

  @Test
  public void getCreateAt_instantiatesWithCurrentTime_today() {
    Task myTask = new Task("Mow the lawn");
    assertEquals(LocalDateTime.now().getDayOfWeek(), myTask.getCreatedAt().getDayOfWeek());
  }

  @Test
 public void all_returnsAllInstancesOfTask_true() {
   Task firstTask = new Task("Mow the lawn");
   Task secondTask = new Task("Buy groceries");
   assertTrue(Task.all().contains(firstTask));
   assertTrue(Task.all().contains(secondTask));
 }

  @Test
  public void newId_tasksInstantiateWithAnID_true() {
    Task myTask = new Task("Mow the lawn");
    assertEquals(Task.all().size(), myTask.getId());
  }

  @Test
   public void find_returnsTaskWithSameId_secondTask() {
     Task firstTask = new Task("Mow the lawn");
     Task secondTask = new Task("Buy groceries");
     assertEquals(Task.find(secondTask.getId()), secondTask);
   }

   @Test
    public void find_returnsNullWhenNoTaskFound_null() {
      assertTrue(Task.find(999) == null);
    }

    @Test
  public void clear_emptiesAllTasksFromArrayList() {
    Task myTask = new Task("Mow the lawn");
    Task.clear();
    assertEquals(Task.all().size(), 0);
  }
}
