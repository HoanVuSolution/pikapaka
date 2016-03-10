package loading;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RemoteViews.RemoteView;

/**
 * ȭ�鿡 ������ �ڵ����� �����ϰ� ������ ������ �ߴܵǴ�
 * ���ϸ��̼��̹������Դϴ�. 
 * 
 * @author Eye
 */
@RemoteView
public class AnimatedImageView extends ImageView
{
	private AnimationDrawable mAnim;
	private boolean mAttached;

	/**
	 * �⺻ ���� (1)
	 * 
	 * @param context ���ؽ�Ʈ
	 */
	public AnimatedImageView(Context context)
	{
		super(context);
	}

	/**
	 * �⺻ ���� (2)
	 * 
	 * @param context ����
	 * @param attrs �Ӽ���
	 */
	public AnimatedImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * �⺻ ���� (3)
	 * 
	 * @param context ����
	 * @param attrs �Ӽ���
	 * @param defStyle ���ǵ� ��Ÿ��
	 */
	public AnimatedImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * �ִϸ��̼��� �����ϰ� �����մϴ�.
	 */
	private void updateAnim()
	{
		Drawable drawable = getDrawable();
		if(mAttached && mAnim != null)
		{
			mAnim.stop();
		}
		if(drawable instanceof AnimationDrawable)
		{
			mAnim = (AnimationDrawable)drawable;
			if (mAttached)
			{
				mAnim.start();
			}
		}
		else
		{
			mAnim = null;
		}
	}

	/* (non-Javadoc)
	 * @see android.widget.ImageView#setImageDrawable(android.graphics.drawable.Drawable)
	 */
	@Override
	public void setImageDrawable(Drawable drawable)
	{
		super.setImageDrawable(drawable);
		updateAnim();
	}

	/* (non-Javadoc)
	 * @see android.widget.ImageView#setImageResource(int)
	 */
	@Override
	public void setImageResource(int resid)
	{
		super.setImageDrawable(null);
		super.setImageResource(resid);
		updateAnim();
	}

	/* (non-Javadoc)
	 * @see android.view.View#onAttachedToWindow()
	 */
	@Override
	public void onAttachedToWindow()
	{
		super.onAttachedToWindow();
		if (mAnim != null)
		{
			mAnim.start();
		}
		mAttached = true;
	}

	/* (non-Javadoc)
	 * @see android.view.View#onDetachedFromWindow()
	 */
	@Override
	public void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		if (mAnim != null)
		{
			mAnim.stop();
		}
		mAttached = false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable
	{
		clearAnimation();
		super.finalize();
	}
}
