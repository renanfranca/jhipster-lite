export class LandscapeScroller {
  scroll(element: HTMLElement, scrollX: number, scrollY: number) {
    element.scroll(scrollX, scrollY);
  }

  scrollIntoView(element: HTMLElement) {
    element.scrollIntoView({ behavior: 'smooth', block: 'center', inline: 'center' });
  }
}
