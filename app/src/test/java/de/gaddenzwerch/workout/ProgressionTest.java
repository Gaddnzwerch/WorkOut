package de.gaddenzwerch.workout;

import org.junit.Test;

import de.gaddenzwerch.workout.model.Exercise;
import de.gaddenzwerch.workout.model.Progression;
import de.gaddenzwerch.workout.model.ProgressionLevel;
import de.gaddenzwerch.workout.model.Unit;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ProgressionTest {


    @Test
    public void create_exercises_isCorrect() throws Exception {
        Exercise exercise1 = new Exercise("Leg assisted pull ups", "Rest your legs", null);
        assertEquals("failure - exercise.getExerciseName() is not equal", exercise1.getExerciseName(), "Leg assisted pull ups");
        assertEquals("failure - exercise.getExerciseDescription() is not equal", exercise1.getExerciseDescription(), "Rest your legs");
        assertNotNull(exercise1.getId());
    }

    @Test
    public void create_progression_isCorrect() throws Exception {
        Progression progression = new Progression("Pull ups");
        Exercise exercise1 = new Exercise("Leg assisted pull ups", "Rest your legs", null);
        progression.addExercise(exercise1);
        assertEquals("failure - progression.getNumberOfProgressions() is not equal", progression.getNumberOfProgressions(), 1);
        Unit unit1 = progression.createUnit(1, 1);
        assertEquals("failure - unit1 not created correctly", "\n Leg assisted pull ups 5 x\n Leg assisted pull ups 4 x\n Leg assisted pull ups 4 x" , unit1.toString());
        unit1 = progression.createUnit(1, 11);
        assertNotNull("failure - progression.createUnit(1, 10) did not return a Unit", unit1);
        assertEquals("failure - unit1 not created correctly", "\n Leg assisted pull ups 8 x\n Leg assisted pull ups 8 x\n Leg assisted pull ups 7 x" , unit1.toString());
        unit1 = progression.createUnit(1, 13);
        assertEquals("failure - unit1 not created correctly", "\n Leg assisted pull ups 8 x\n Leg assisted pull ups 8 x\n Leg assisted pull ups 7 x" , unit1.toString());
    }
}