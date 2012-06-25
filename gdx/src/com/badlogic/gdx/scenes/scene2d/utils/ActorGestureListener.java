
package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ActorEvent;
import com.badlogic.gdx.scenes.scene2d.ActorListener;

/** Detects tap, longPress, fling, pan, zoom, and pinch gestures.
 * @see GestureDetector
 * @author Nathan Sweet */
public class ActorGestureListener extends ActorListener {
	private final GestureDetector detector = new GestureDetector(new GestureAdapter() {
		public boolean tap (float x, float y, int count) {
			ActorGestureListener.this.tap(event, x, y, count);
			return true;
		}

		public boolean longPress (float x, float y) {
			return ActorGestureListener.this.longPress(event, x, y);
		}

		public boolean fling (float velocityX, float velocityY) {
			ActorGestureListener.this.fling(event, velocityX, velocityY);
			return true;
		}

		public boolean pan (float x, float y, float deltaX, float deltaY) {
			ActorGestureListener.this.pan(event, x, y, deltaX, deltaY);
			return true;
		}

		public boolean zoom (float initialDistance, float distance) {
			ActorGestureListener.this.zoom(event, initialDistance, distance);
			return true;
		}

		public boolean pinch (Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
			ActorGestureListener.this.pinch(event, initialPointer1, initialPointer2, pointer1, pointer2);
			return true;
		}
	});

	ActorEvent event;

	public boolean touchDown (ActorEvent event, float x, float y, int pointer, int button) {
		this.event = event;
		detector.touchDown(x, y, pointer, 0);
		return true;
	}

	public void touchUp (ActorEvent event, float x, float y, int pointer, int button) {
		this.event = event;
		detector.touchUp(x, y, pointer, 0);
	}

	public void touchDragged (ActorEvent event, float x, float y, int pointer) {
		this.event = event;
		detector.touchDragged(x, y, pointer);
	}

	public void tap (ActorEvent event, float x, float y, int count) {
	}

	/** If true is returned, additional gestures will not be triggered. */
	public boolean longPress (ActorEvent event, float x, float y) {
		return false;
	}

	public void fling (ActorEvent event, float velocityX, float velocityY) {
	}

	public void pan (ActorEvent event, float x, float y, float deltaX, float deltaY) {
	}

	public void zoom (ActorEvent event, float initialDistance, float distance) {
	}

	public void pinch (ActorEvent event, Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
	}

	public GestureDetector getGestureDetector () {
		return detector;
	}
}
