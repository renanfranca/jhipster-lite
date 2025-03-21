import { RANKS } from '@/module/domain/landscape/ModuleRank';
import { ModuleRankStatistics, toModuleRankStatistics } from '@/module/domain/ModuleRankStatistics';
import { LandscapeRankModuleFilterVue } from '@/module/primary/landscape-rank-module-filter';
import { VueWrapper, mount } from '@vue/test-utils';
import { describe, expect, it } from 'vitest';
import { wrappedElement } from '../../../WrappedElement';
import { defaultLandscape } from '../../domain/landscape/Landscape.fixture';

const wrap = (props?: { moduleRankStatistics: ModuleRankStatistics }): VueWrapper => {
  return mount(LandscapeRankModuleFilterVue, {
    props: props || {
      moduleRankStatistics: RANKS.map(rank => ({ rank, quantity: 1 })), // Force enable all ranks
    },
  });
};

describe('LandscapeRankModuleFilterComponent', () => {
  it('should display all rank filters', () => {
    const wrapper = wrap();

    const buttons = wrapper.findAll('[data-testid^="rank-"]');
    expect(buttons).toHaveLength(RANKS.length);
  });

  it('should select rank when clicking on filter button', async () => {
    const wrapper = wrap();

    const rankDButton = wrapper.find(wrappedElement('rank-RANK_D-filter'));
    await rankDButton.trigger('click');

    expect(wrapper.emitted('selected')).toEqual([[RANKS[0]]]);
  });

  it('should deselect rank when clicking on selected filter button', async () => {
    const wrapper = wrap();

    const rankDButton = wrapper.find(wrappedElement('rank-RANK_D-filter'));
    await rankDButton.trigger('click');
    await rankDButton.trigger('click');

    expect(wrapper.emitted('selected')).toEqual([[RANKS[0]], [undefined]]);
  });

  it('should format rank short name correctly', () => {
    const wrapper = wrap();

    const buttons = wrapper.findAll('[data-testid^="short-name-"]');
    RANKS.forEach((rank, index) => {
      expect(buttons[index].text()).toBe(rank.replace('RANK_', ''));
    });
  });
  it('should format rank full name correctly', () => {
    const wrapper = wrap();

    const buttons = wrapper.findAll('[data-testid^="full-name-"]');
    RANKS.forEach((rank, index) => {
      expect(buttons[index].text()).toBe(rank.replace('RANK_', 'RANK '));
    });
  });

  it('should apply selected style to active filter', async () => {
    const wrapper = wrap();

    const rankDButton = wrapper.find(wrappedElement('rank-RANK_D-filter'));
    await rankDButton.trigger('click');

    expect(rankDButton.classes()).toContain('-active');
  });

  it('should emit selected rank when clicking a filter button', async () => {
    const wrapper = wrap();

    const rankAButton = wrapper.find(wrappedElement('rank-RANK_A-filter'));
    await rankAButton.trigger('click');
    await wrapper.vm.$nextTick();

    expect(wrapper.emitted('selected')).toEqual([[RANKS[3]]]);
  });

  it('should emit undefined when deselecting a rank', async () => {
    const wrapper = wrap();

    const rankSButton = wrapper.find(wrappedElement('rank-RANK_S-filter'));
    await rankSButton.trigger('click');
    await rankSButton.trigger('click');

    expect(wrapper.emitted('selected')).toEqual([[RANKS[4]], [undefined]]);
  });

  it('should only emit one rank at a time', async () => {
    const wrapper = wrap();

    const rankBButton = wrapper.find(wrappedElement('rank-RANK_B-filter'));
    const rankCButton = wrapper.find(wrappedElement('rank-RANK_C-filter'));
    await rankBButton.trigger('click');
    await rankCButton.trigger('click');

    expect(wrapper.emitted('selected')).toEqual([[RANKS[2]], [RANKS[1]]]);
  });

  it('should display correct description for rank button', () => {
    const wrapper = wrap();

    const rankDButton = wrapper.find(wrappedElement('rank-RANK_D-filter'));

    expect(rankDButton.attributes('title')).toBe('Experimental or advanced module requiring specific expertise');
  });

  it('should disable rank button without module rank associated', () => {
    const moduleRankStatistics = toModuleRankStatistics(defaultLandscape());
    const wrapper = wrap({ moduleRankStatistics });

    const rankSButton = wrapper.find(wrappedElement('rank-RANK_S-filter'));
    const rankAButton = wrapper.find(wrappedElement('rank-RANK_A-filter'));
    const rankBButton = wrapper.find(wrappedElement('rank-RANK_B-filter'));
    const rankCButton = wrapper.find(wrappedElement('rank-RANK_C-filter'));
    const rankDButton = wrapper.find(wrappedElement('rank-RANK_D-filter'));

    expect(rankSButton.attributes('disabled')).toBeUndefined();
    expect(rankDButton.attributes('disabled')).toBeUndefined();
    expect(rankCButton.attributes('disabled')).toBeUndefined();
    expect(rankAButton.attributes('disabled')).toBeUndefined();
    expect(rankBButton.attributes('disabled')).toBeDefined();
  });
});
