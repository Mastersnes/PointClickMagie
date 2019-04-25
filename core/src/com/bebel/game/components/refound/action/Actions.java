/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.bebel.game.components.refound.action;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.bebel.game.components.refound.action.ordonnancement.*;
import com.bebel.game.components.refound.action.time.*;
import com.bebel.game.components.refound.element.Animate;
import com.bebel.game.components.refound.runnable.FinishRunnable;

import static com.badlogic.gdx.math.Interpolation.linear;
import static com.badlogic.gdx.utils.Align.bottomLeft;

public class Actions {
	protected static <T extends AbstractAction> T action (final Class<T> type) {
		final Pool<T> pool = Pools.get(type);
		final T action = pool.obtain();
		return action;
	}

	public static MoveToAction moveTo (final float x, final float y, final float duration) {
		return moveTo(x, y, bottomLeft, duration, linear);
	}
	public static MoveToAction moveTo (final float x, final float y, final int align, final float duration, final Interpolation interpolation) {
		MoveToAction action = action(MoveToAction.class);
		action.setPosition(x, y, align);
		action.setDuration(duration);
		action.setInterpolation(interpolation);
		return action;
	}

	static public MoveByAction moveBy (float amountX, float amountY, float duration) {
		return moveBy(amountX, amountY, bottomLeft, duration, linear);
	}
	static public MoveByAction moveBy (float amountX, float amountY, int align, float duration, Interpolation interpolation) {
		MoveByAction action = action(MoveByAction.class);
		action.setAmount(amountX, amountY);
		action.setDuration(duration);
		action.setInterpolation(interpolation);
		action.setAlignment(align);
		return action;
	}

	static public ScaleToAction scaleTo (float x, float y, float duration) {
		return scaleTo(x, y, duration, linear);
	}
	static public ScaleToAction scaleTo (float x, float y, float duration, Interpolation interpolation) {
		ScaleToAction action = action(ScaleToAction.class);
		action.setScale(x, y);
		action.setDuration(duration);
		action.setInterpolation(interpolation);
		return action;
	}

	static public ScaleByAction scaleBy (float amountX, float amountY, float duration) {
		return scaleBy(amountX, amountY, duration, linear);
	}
	static public ScaleByAction scaleBy (float amountX, float amountY, float duration, Interpolation interpolation) {
		ScaleByAction action = action(ScaleByAction.class);
		action.setAmount(amountX, amountY);
		action.setDuration(duration);
		action.setInterpolation(interpolation);
		return action;
	}

	static public RotateToAction rotateTo (float rotation, float duration) {
		return rotateTo(rotation, duration, linear);
	}
	static public RotateToAction rotateTo (float rotation, float duration, Interpolation interpolation) {
		RotateToAction action = action(RotateToAction.class);
		action.setRotation(rotation);
		action.setDuration(duration);
		action.setInterpolation(interpolation);
		return action;
	}

	static public RotateByAction rotateBy (float rotationAmount, float duration) {
		return rotateBy(rotationAmount, duration, linear);
	}

	static public RotateByAction rotateBy (float rotationAmount, float duration, Interpolation interpolation) {
		RotateByAction action = action(RotateByAction.class);
		action.setAmount(rotationAmount);
		action.setDuration(duration);
		action.setInterpolation(interpolation);
		return action;
	}

	/** Transitions from the color at the time this action starts to the specified color. */
	static public ColorAction color (Color color, float duration) {
		return color(color, duration, linear);
	}

	/** Transitions from the color at the time this action starts to the specified color. */
	static public ColorAction color (Color color, float duration, Interpolation interpolation) {
		ColorAction action = action(ColorAction.class);
		action.setEndColor(color);
		action.setDuration(duration);
		action.setInterpolation(interpolation);
		return action;
	}

	/** Transitions from the alpha at the time this action starts to the specified alpha. */
	static public AlphaAction alpha (float a, float duration) {
		return alpha(a, duration, linear);
	}

	/** Transitions from the alpha at the time this action starts to the specified alpha. */
	static public AlphaAction alpha (float a, float duration, Interpolation interpolation) {
		AlphaAction action = action(AlphaAction.class);
		action.setAlpha(a);
		action.setDuration(duration);
		action.setInterpolation(interpolation);
		return action;
	}

	/** Transitions from the alpha at the time this action starts to an alpha of 0. */
	static public AlphaAction fadeOut (float duration) {
		return alpha(0, duration, linear);
	}

	/** Transitions from the alpha at the time this action starts to an alpha of 0. */
	static public AlphaAction fadeOut (float duration, Interpolation interpolation) {
		AlphaAction action = action(AlphaAction.class);
		action.setAlpha(0);
		action.setDuration(duration);
		action.setInterpolation(interpolation);
		return action;
	}

	/** Transitions from the alpha at the time this action starts to an alpha of 1. */
	static public AlphaAction fadeIn (float duration) {
		return alpha(1, duration, linear);
	}

	/** Transitions from the alpha at the time this action starts to an alpha of 1. */
	static public AlphaAction fadeIn (float duration, Interpolation interpolation) {
		AlphaAction action = action(AlphaAction.class);
		action.setAlpha(1);
		action.setDuration(duration);
		action.setInterpolation(interpolation);
		return action;
	}

	static public DelayAction delay (float duration) {
		DelayAction action = action(DelayAction.class);
		action.setDuration(duration);
		return action;
	}

	static public DelayAction delay (float duration, AbstractAction delayedAction) {
		DelayAction action = action(DelayAction.class);
		action.setDuration(duration);
		action.setAction(delayedAction);
		return action;
	}

	static public SequenceAction sequence (AbstractAction... actions) {
		SequenceAction action = action(SequenceAction.class);
		action.addActions(actions);
		return action;
	}

	static public ParallelAction parallel (AbstractAction... actions) {
		ParallelAction action = action(ParallelAction.class);
		action.addActions(actions);
		return action;
	}

	static public RepeatAction repeat (int count, AbstractAction repeatedAction) {
		RepeatAction action = action(RepeatAction.class);
		action.setCount(count);
		action.setAction(repeatedAction);
		return action;
	}

	static public RepeatAction forever (AbstractAction repeatedAction) {
		RepeatAction action = action(RepeatAction.class);
		action.setCount(RepeatAction.FOREVER);
		action.setAction(repeatedAction);
		return action;
	}

	static public RunnableAction run (Runnable runnable) {
		RunnableAction action = action(RunnableAction.class);
		action.setRunnable(runnable);
		return action;
	}

	/**
	 * Action qui ne fait rien
	 * @return
	 */
	public static RunnableAction doNothing() {
		RunnableAction action = action(RunnableAction.class);
		action.setRunnable(new FinishRunnable() {
			@Override
			public void run() {
				finish = true;
			}
		});
		return action;
	}

	public static RunnableAction finishRun(final FinishRunnable runnable) {
		RunnableAction action = action(RunnableAction.class);
		action.setRunnable(runnable);
		return action;
	}

	/**
	 * Animation qui ne fait rien
	 * @return
	 */
	public static FinishAnimAction emptyAnim() {
		FinishAnimAction action = action(FinishAnimAction.class);
		return action;
	}
	public static FinishAnimAction finishAnim(final Animate animation, final boolean flipH, final boolean flipV) {
		FinishAnimAction action = action(FinishAnimAction.class);
		action.init(animation, flipH, flipV);
		return action;
	}
}
