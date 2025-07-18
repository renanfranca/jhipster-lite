import { WindowApplicationListener } from '@/shared/alert/infrastructure/primary/WindowApplicationListener';
import { describe, expect, it, vi } from 'vitest';

describe('WindowApplicationListener', () => {
  it('should add event listener on window', () => {
    vi.spyOn(window, 'addEventListener');

    const windowApplicationListener = new WindowApplicationListener(window);

    windowApplicationListener.addEventListener('success', vi.fn());

    expect(window.addEventListener).toHaveBeenCalledOnce();
  });

  it('should remove event listener on window', () => {
    vi.spyOn(window, 'removeEventListener');

    const windowApplicationListener = new WindowApplicationListener(window);

    windowApplicationListener.removeEventListener('success', vi.fn());

    expect(window.removeEventListener).toHaveBeenCalledOnce();
  });
});
