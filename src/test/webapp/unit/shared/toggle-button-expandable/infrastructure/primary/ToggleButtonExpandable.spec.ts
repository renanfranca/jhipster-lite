import { ToggleButtonExpandableVue } from '@/shared/toggle-button-expandable/infrastructure/primary';
import { mount } from '@vue/test-utils';
import { describe, expect, it } from 'vitest';

describe('ToggleButtonExpandable', () => {
  describe('when initially rendered', () => {
    it('should display short name by default', () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
        },
      });

      const shortName = wrapper.find('[data-testid="short-name-S"]');

      expect(shortName.exists()).toBe(true);
      expect(shortName.text()).toBe('S');
    });

    it('should display full name when initialized as active', () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
          isActive: true,
        },
      });

      const shortName = wrapper.find('[data-testid="short-name-S"]');
      expect(shortName.exists()).toBe(false);
      const fullName = wrapper.find('[data-testid="full-name-S"]');
      expect(fullName.exists()).toBe(true);
    });
  });

  describe('when customizing appearance', () => {
    it('should highlight when active', () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
          isActive: true,
        },
      });

      expect(wrapper.classes()).toContain('-active');
    });

    it('should apply additional styling when provided', () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
          customClasses: ['-rank-color -s'],
        },
      });

      expect(wrapper.classes()).toContain('-rank-color');
      expect(wrapper.classes()).toContain('-s');
    });
  });

  describe('when interacting with the button', () => {
    it('should notify when clicked', async () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
        },
      });

      await wrapper.trigger('click');

      expect(wrapper.emitted('click')).toBeTruthy();
      expect(wrapper.emitted('click')?.length).toBe(1);
    });

    it('should prevent interaction when disabled', () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
          disabled: true,
        },
      });

      expect(wrapper.attributes('disabled')).toBe('');
    });
  });

  describe('when hovering over the button', () => {
    it('should expand to show full name', async () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
        },
      });
      const shortName = wrapper.find('[data-testid="short-name-S"]');
      expect(shortName.exists()).toBe(true);
      expect(shortName.isVisible()).toBe(true);

      await wrapper.trigger('mouseenter');
      const fullNameElement = wrapper.find('[data-testid="full-name-S"]');
      await fullNameElement.trigger('transitionend', {
        propertyName: 'opacity',
      });

      const fullName = wrapper.find('[data-testid="full-name-S"]');
      expect(fullName.exists()).toBe(true);
      const shortNameExpected = wrapper.find('[data-testid="short-name-S"]');
      expect(shortNameExpected.exists()).toBe(false);
    });

    it('should collapse back to short name after mouse leaves', async () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
        },
      });
      let shortName = wrapper.find('[data-testid="short-name-S"]');
      expect(shortName.exists()).toBe(true);
      await wrapper.trigger('mouseenter');
      const fullNameElement = wrapper.find('[data-testid="full-name-S"]');
      await fullNameElement.trigger('transitionend', {
        propertyName: 'opacity',
      });
      shortName = wrapper.find('[data-testid="short-name-S"]');
      expect(shortName.exists()).toBe(false);

      await wrapper.trigger('mouseleave');
      await fullNameElement.trigger('transitionend', {
        propertyName: 'opacity',
      });

      shortName = wrapper.find('[data-testid="short-name-S"]');
      expect(shortName.exists()).toBe(true);
    });

    it('should only respond to completed opacity transitions when expanding', async () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
        },
      });
      let shortName = wrapper.find('[data-testid="short-name-S"]');
      expect(shortName.exists()).toBe(true);

      await wrapper.trigger('mouseenter');
      const fullNameElement = wrapper.find('[data-testid="full-name-S"]');
      await fullNameElement.trigger('transitionend', {
        propertyName: 'max-width',
      });

      shortName = wrapper.find('[data-testid="short-name-S"]');
      expect(shortName.exists()).toBe(true);
    });
  });

  describe('when changing state programmatically', () => {
    it('should expand when becoming active', async () => {
      const wrapper = mount(ToggleButtonExpandableVue, {
        props: {
          shortName: 'S',
          fullName: 'RANK S',
          isActive: false,
        },
      });
      let shortName = wrapper.find('[data-testid="short-name-S"]');
      expect(shortName.exists()).toBe(true);

      await wrapper.setProps({ isActive: true });
      const fullNameElement = wrapper.find('[data-testid="full-name-S"]');
      await fullNameElement.trigger('transitionend', {
        propertyName: 'opacity',
      });

      shortName = wrapper.find('[data-testid="short-name-S"]');
      expect(shortName.exists()).toBe(false);
      const fullName = wrapper.find('[data-testid="full-name-S"]');
      expect(fullName.exists()).toBe(true);
    });
  });
});
