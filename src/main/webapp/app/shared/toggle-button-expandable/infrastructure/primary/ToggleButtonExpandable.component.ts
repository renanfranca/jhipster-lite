import { defineComponent, ref } from 'vue';

export default defineComponent({
  name: 'ToggleButtonExpandableVue',
  props: {
    shortName: {
      type: String,
      required: true,
    },
    fullName: {
      type: String,
      required: true,
    },
    isActive: {
      type: Boolean,
      default: false,
    },
    customClasses: {
      type: Array as () => string[],
      default: () => [],
    },
    dataTestId: {
      type: String,
      default: '',
    },
    title: {
      type: String,
      default: '',
    },
    disabled: {
      type: Boolean,
      default: false,
    },
  },
  emits: ['click', 'mouseenter', 'mouseleave'],
  setup(props, { emit }) {
    const isHovered = ref(false);
    const fullNameVisible = ref(props.isActive);

    const buttonClasses = (): string => {
      return [...props.customClasses, props.isActive ? '-active' : ''].filter(Boolean).join(' ');
    };

    const isShortNameVisible = (): boolean => {
      return !fullNameVisible.value;
    };

    const handleClick = (): void => emit('click');

    const handleMouseEnter = (): void => {
      isHovered.value = true;
      emit('mouseenter');
    };

    const handleMouseLeave = (): void => {
      isHovered.value = false;
      emit('mouseleave');
    };

    const handleFullNameTransitionEnd = (event: TransitionEvent): void => {
      if (event.propertyName === 'opacity') {
        fullNameVisible.value = isHovered.value || props.isActive;
      }
    };

    return {
      buttonClasses,
      isShortNameVisible,
      handleClick,
      handleMouseEnter,
      handleMouseLeave,
      handleFullNameTransitionEnd,
    };
  },
});
