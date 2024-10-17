import { LandscapeScroller } from '@/module/primary/landscape/LandscapeScroller';
import { describe, expect, it, vi } from 'vitest';

interface HTMLElementStub extends HTMLElement {
  scroll: vi.fn;
  scrollIntoView: vi.fn;
}
const stubHtmlElement = (): HTMLElementStub =>
  ({
    scroll: vi.fn(),
    scrollIntoView: vi.fn(),
  }) as HTMLElementStub;

describe('LandscapeScroller', () => {
  it('should scroll', () => {
    const element = stubHtmlElement();
    const landscapeScroller = new LandscapeScroller();

    landscapeScroller.scroll(element, 10, 10);

    expect(element.scroll).toHaveBeenCalledTimes(1);
    expect(element.scroll).toBeCalledWith(10, 10);
  });

  it('should scroll smooth', () => {
    const element = stubHtmlElement();
    const landscapeScroller = new LandscapeScroller();

    landscapeScroller.scrollSmooth(element, 10, 10);

    expect(element.scroll).toHaveBeenCalledTimes(1);
    expect(element.scroll).toBeCalledWith({ left: 10, top: 10, behavior: 'smooth' });
  });

  it('should scroll into view', () => {
    const element = stubHtmlElement();
    const landscapeScroller = new LandscapeScroller();

    landscapeScroller.scrollIntoView(element);

    expect(element.scrollIntoView).toHaveBeenCalledTimes(1);
    expect(element.scrollIntoView).toBeCalledWith({ behavior: 'smooth', block: 'center', inline: 'center' });
  });
});
